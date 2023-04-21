/**
 * 함수명이 대문자로 시작하면 관례적으로 생성자 함수 입니다.
 * @param {*} uiBtnPrevMonthId 이전 button id
 * @param {*} uiBtnNextMonthId  다음 button id
 * @param {*} uiBtnCurrentMonthId 오늘 button id
 * @returns 
 */
function Navigator(uiBtnPrevMonthId, uiBtnNextMonthId, uiBtnCurrentMonthId){
    'use strict'

    let year = null;
    let month = null;

    this.uiBtnPrevMonthId = uiBtnPrevMonthId;
    this.uiBtnNextMonthId = uiBtnNextMonthId;
    this.uiBtnCurrentMonthId = uiBtnCurrentMonthId;

    /*
     //즉시 실행함수 : url 주소를 기준으로 year, month를 얻습니다. ???
     //url 주소 : ..../index.html?year=2023&month=04
     //year == null or year == null 이면 오늘 날짜로 설정합니다.
     //month <10 이면 01,02,03 .. 형태로 설정합니다.
     */
    (function(){
        // https://developer.mozilla.org/en-US/docs/Web/API/URLSearchParams/get
        const searchParam = new URLSearchParams(document.location.search);

        year = searchParam.get("year");
        month = searchParam.get("month");
        let today = new Date();
    
    
        // https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Date/getFullYear
        if(year == null){
            year = today.getFullYear();
        }
    
        // https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Date/getMonth
        if(month == null){
            month = today.getMonth()+1;
            month =  _convertToZeroMonthAndDay(month);
        }

    })();


    //button event 설정 , DOMContentLoaded 시점에 ..
    window.addEventListener("DOMContentLoaded",function(){

        let btnPrevMonth = document.getElementById(uiBtnPrevMonthId);
        let btnNextMonth = document.getElementById(uiBtnNextMonthId);
        let btnCurrentMonth = document.getElementById(uiBtnCurrentMonthId);

        https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Error
        if(btnPrevMonth == null){
            throw new Error('이전 버튼이 없습니다.');
        }
        if(btnNextMonth == null){
            throw new Error('다음 버튼이 없습니다.');
        }
        if(btnCurrentMonth == null){
            throw new Error('오늘 버튼이 없습니다.')
        }

        //버튼 이벤트 등록
        //이전
        btnPrevMonth.addEventListener("click",function(){
            let targetYear=null;
            let targetMonth=null;

            if(month == 1) {
                targetYear = parseInt(year)-1;
                targetMonth = 12;
            } else {
                targetYear = year;
                targetMonth = parseInt(month)-1;
            }
            _navigate(targetYear,targetMonth);
        });

        //다음
        btnNextMonth.addEventListener("click",function(){
            let targetYear=null;
            let targetMonth=null;
            
            if(month == 12) {
                targetYear = parseInt(year)+1;
                targetMonth = 1;
            } else {
                targetYear = year;
                targetMonth = parseInt(month)+1;
            }

            _navigate(targetYear,targetMonth);
        });
        //오늘
        btnCurrentMonth.addEventListener("click",function(){
            let targetYear=null;
            let targetMonth=null;
            
            let today = new Date();
            targetYear = today.getFullYear();
            targetMonth = today.getMonth()+1;

            _navigate(targetYear,targetMonth);
        });
    });

    function _navigate(targetYear,targetMonth){
       //페이지 이동 : ./index.html?year=2023&month=03
       //https://developer.mozilla.org/en-US/docs/Web/API/Location
       console.log("test");
       location.href="/?year=" + targetYear + "&month=" + _convertToZeroMonthAndDay(targetMonth);
    }

    //https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/parseInt
    function _convertToZeroMonthAndDay(d){
       //...
       if( d < 10) {
            d = "0"+d;
       }

        return d;
    }

    return {
        getYear : function(){
            return year;
        },
        getMonth : function(){
            return month;
        },
        convertToZeroMonthAndDay : function(d){
            return _convertToZeroMonthAndDay(d);
        }
    }

}