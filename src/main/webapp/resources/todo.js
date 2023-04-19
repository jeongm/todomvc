'use strict';
const navi = new Navigator("btn-prev-month","btn-next-month","btn-current-month");
// const mStore = memoryStore();
// const store = localStorageStore();
const store = apiStore();


(function(){
    'use strict';

    window.addEventListener("DOMContentLoaded",(event)=>{
        displayYearMonth(navi.getYear(), navi.getMonth());
        createTodoList();
    });

    function displayYearMonth(targetYear, targetMonth){
        // html element : todo-nav-year 표시
        const todoNavYear = document.getElementById("todo-nav-year");
        todoNavYear.innerText = targetYear 
        // html element : todo-nav-month 표시
        const todoNavMonth = document.getElementById("todo-nav-month");
        todoNavMonth.innerText = targetMonth;
    }

    async function createTodoList(){

        const year = navi.getYear();
        const month = navi.getMonth();
    
        const daysInMonth = getDaysInMonth(year,month);
        // console.log("daysInMonth:" + daysInMonth);
        const todoItemContainer = document.getElementById("todo-item-container");
        const template = document.getElementById("todo-item-template");
    
        for(let i=1; i<=daysInMonth; i++ ){
           //https://developer.mozilla.org/ko/docs/Web/API/Document/importNode
            const todoItem = document.importNode(template.content,true);
            const todoItemDay = todoItem.querySelector(".todo-item-day");


            //날짜표시
            todoItemDay.innerText="";
            const span1 = document.createElement("span");
            span1.innerText=i;
            todoItemDay.appendChild(span1);
    
            //form 날짜 설정 name=date
            //https://developer.mozilla.org/ko/docs/Web/API/Document/querySelector
            const todoDate = todoItem.querySelector("input[name=todoDate]");
            todoDate.value=year + "-" + month + "-" + navi.convertToZeroMonthAndDay(i);

            //form 전송 event
            const form = todoItem.querySelector("form");
            form.addEventListener("submit",todoSubmit);

            const deleteBtn = todoItem.querySelector("button[class=btn-remove-all]");
            deleteBtn.addEventListener("click",async function() {
                if(confirm(`$todoDate.value`+"일정을 모두 삭제하시겠습니까?")) {
                    await store.deleteByTodoDate(todoDate.value);
                    clearTodoItemList(todoDate.value);
                } 
            });
    
            const todoItemList = todoItem.querySelector(".todo-item-list");
            todoItemList.setAttribute("id","todo-item-list-" + todoDate.value);
            
            
            todoItemContainer.appendChild(todoItem);
            await displayTodoItemList(todoDate.value);
            //TODO#1 - 구현 .. 

            
        }
    };

    //ex) 2023-02 = 28일 , 2023-03 = 31 .. 해당달의 day count 구하기
    function getDaysInMonth(targetYear, targetMonth){
        return new Date(targetYear, parseInt(targetMonth), 0).getDate();
    }

    // form event 처리
    async function todoSubmit(event){
        event.preventDefault();
        const validateForm = function(form){
            if(form["todoSubject"].value.trim() == "") {
                alert('todo를 입력해주세요ㅜ');
                form["todoSubject"].focus();
                return false;
            }
            return true;
        };
        if(validateForm(event.target)) {
            const todoDate = event.target['todoDate'].value;
            const todoSubject = event.target['todoSubject'].value;
            try{
                await store.save(todoDate,todoSubject);
                await displayTodoItemList(todoDate);
            }catch(e) {
                alert(e);
            }
        }
        
    }

    function clearTodoItemList(todoDate) {
        const todoItemList = document.getElementById("todo-item-list-"+todoDate);
        if (todoItemList) {
            while(todoItemList.firstChild) {
                todoItemList.removeChild(todoItemList.firstChild)
            }
        }

    }
    
    
    async function displayTodoItemList(todoDate) {
        clearTodoItemList(todoDate);
        const todoItems = await store.getTodoItemList(todoDate);
        const ul = document.getElementById("todo-item-list-"+todoDate);

        for(let i = 0; i < todoItems.length; i++) {
            const li = document.createElement("li");
            li.innerText=todoItems[i].subject;
            ul.appendChild(li);
        }

    }
    
})();

