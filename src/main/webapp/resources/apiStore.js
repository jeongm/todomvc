//https://developer.mozilla.org/ko/docs/Web/API/Fetch_API/Using_Fetch

const apiStore = function(){
    'use strict';
    //api 서버 주소입니다.
    const SERVER_URL="http://133.186.144.236:8100";

    //user id는 변경해주세요
    const X_USER_ID = "jeong";
    const DAILY_MAX_TODO_COUNT=8;
    const api = new Object();

    //공통으로 사용하는 header 입니다. 인증이 필요한 부분은 user 아이디가 포함됩니다.
    //X-USER-ID가 header에 포함되지 않는다면 error 발생할 수 있습니다.
    const headers ={
        'Content-Type' : 'application/json',
        'X-USER-ID' : X_USER_ID,
    }

    /*#1 저장구현하기
        api 서버를 이용해서 저장을 합니다.
        별도의 uuid를 만들어서 사용할 필요 없습니다.
        비동기 통신을 위해서 async await fetch를 사용합니다.
        api 명세서를 참고하여 구현합니다.
        https://nhnacademy.dooray.com/share/pages/7i6-W8S2TI6e-oKUJVL57g/3373349495003408976
    */
    api.save= async function(todoDate, todoSubject){

        /*#1-1 해당 날짜에 >=DAILY_MAX_TODO_COUNT 이면 적절한 Error 발생 시키기.
            countByTodoDate(todoDate); 사용해서 등록 count를 구합니다.
        */

       if(await countByTodoDate(todoDate) >= DAILY_MAX_TODO_COUNT) {
            throw new Error('저장가능한 TODO는 8개입니다.');
       }

        /*#1-2  저장구현

            POST /api/calendar/events
            Content-Type: application/json
            X-USER-ID: marco

            {
                "subject" : "html/css/javasciprt study",
                "eventAt": "2023-02-15"
            }
        */
        const url = SERVER_URL + "/api/calendar/events";
        const data = {
            'subject' : todoSubject,
            'eventAt' : todoDate
        }

        const options = {
            method : 'POST',
            headers:headers,
            body : JSON.stringify(data)
        }


        /*TODO#1-3 응답코드가 2xx가 아니다면 적절한 error처리 */
        //참고로 응답코드가 몇번으로 return 되는지 console.log로 남겨서 직접확인해보세요.

        const response = await fetch(url,options);
        const responseJson = await response.json();

        if(!response.ok) {
            throw new Error("todo list api error");
        }

        return responseJson;


    }

    /*TODO#2 삭제 구현하기
        todo2번 부터는 option 객체를 적절히 생성하셔서 .. 처리해주세요.
    */
    api.delete = async function(todoDate,id){
        const url = SERVER_URL + "/api/calendar/events/" + id;
        //TODO#2-1 삭제 구현.
        const options = {
            method: "DELETE",
            headers: headers
        }
        //TODO#2-2 응답코드를 확인해주세요. error발생시 적절하 error처리 해주세요.
        const response = await fetch(url,options);
    }

    /*TODO#3 해당날짜에 해당되는 모든 todo 삭제하기
    */
    api.deleteByTodoDate= async function(todoDate){
        const url = SERVER_URL + "/api/calendar/events/daily/" + todoDate;

        //#3-1 삭제 구현하기
        const options = {
            method: "DELETE",
            headers: headers
        }

        const response = await fetch(url,options);

        //#3-2 Error 응답이 200번대가 아니라면 적절한 error 처리
        if(!response.ok) {
            throw new Error("todo delete api error");
        }

    }

    //TODO#4 해당 날짜에 존재하는 모든 todo 조회, 존재하지 않는다면 빈 배열을 리턴합니다.
    api.getTodoItemList= async function(todoDate){

        const arr = todoDate.split("-");
        let year = arr[0];
        let month =arr[1];
        let day = arr[2];

        const url = SERVER_URL + "/api/calendar/events/?year=" + year + "&month=" + parseInt(month) + "&day=" + parseInt(day);

        //TODO#4-1 조회 구현
        const options = {
            method: "GET",
            headers:{
                'Accept': 'application/json',
                'X-USER-ID': 'jeong'
            }
        }

        const response = await fetch(url,options);
        const responseJson = await response.json();

        if(!response.ok) {
            throw new Error("todo list api error");
        }

        return responseJson;

        //TODO#4-2 응답 코드를 확인 후 error 처리

        // return [];
    }

    //TODO#5 해당 날짜의 모든 todo item count, 참고로 countByTodoDate 함수는 api 내부에서만 접근가능 합니다.
    async function countByTodoDate(todoDate){
        const url = SERVER_URL + "/api/calendar/daily-register-count?date=" + todoDate;

        //TODO#5-1 구현
        const options = {
            method: "GET",
            headers:{
                'Accept' : 'application/json',
                'X-USER-ID' : X_USER_ID
            }
        }

        const response = await fetch(url,options);
        const responseJson = await response.json();
        
        if(!response.ok){
            throw new Error('todo list api Error');
        }

        return responseJson.count;

        //TODO#5-2 응답 코드를 확인 후 error 처리

    }

    return api;
}