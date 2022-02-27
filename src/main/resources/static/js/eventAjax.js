// function eventSave() {
//     $.ajax({
//         url: "/bbs",
//         type: "GET",
//         dataType: "json",
//     }).done(function (data) {
//         $("#resultDiv").text(JSON.stringify(data));
//         console.log("성공");
//
//     }).fail(function () {
//         console.log("실패");
//     })
// }

function eventSave() {
    var param = {
        'title': $("#title").val(),
        'context': $("#context").val(),
        'textColor': $("#textColor").val(),
        'backgroundColor': $("#backgroundColor").val(),
        'userId' : $("#userId").val(),
        'allDay' : $("#allDay").val(),
        // 'file' : $("#file").val()

    }
    var form = $('#uploadForm')[0];
    var formData = new FormData(form);
    formData.append('key', new Blob([ JSON.stringify(param) ], {type : "application/json"}));
    // console.log(param.allDay);
    // if (param.loginUser === "") {
    //     return;
    // }

    $.ajax({
        url: '/event',
        type: 'POST',
        enctype: 'multipart/form-data',
        contentType : false,
        processData : false,
        data: formData,
    }).done(function () {
        alert("완료");
        window.location.reload();
    }).fail(function () {
            alert("실패");
            // history.back();
        }
    );
}


function searchData() {
    let returnValue;

    $.ajax({
        url: "/event/" + $("#userId").val(),
        type: "GET",
        dataType: "json",
        async:false,

    }).done(function (data) {
        // $("#resultDiv").text(JSON.stringify(data));
        // console.log(JSON.stringify(data));
        console.log("성공");
        returnValue = data;
        // console.log(returnValue);
        // return JSON.stringify(data);

    }).fail(function () {
        console.log("실패");
    });
    return returnValue;
}

function detailViewData(eventId) {

    $.ajax({
        url: "/api/v1/event/" + eventId,
        type: "GET",
        dataType: "json",
    }).done(function (data) {
        console.log(data.start.substring(0, 10) + "\u00a0\u00a0" + data.start.substring(11, 19));
        console.log(data);
        document.getElementById('eventId').value = eventId;
        document.getElementById('viewTitle').innerText = data.title;
        document.getElementById('viewContext').innerText = data.context;
        document.getElementById('viewFile').src  = data.file;
        document.getElementById('uploadDate').innerText = data.start.substring(0, 10) +"\u00a0\u00a0"+ data.start.substring(11, 19);
    }).fail(function () {
        console.log("실패");
    });
}

function updateDetailViewData(eventId) {
    let returnData;
    $.ajax({
        url: "/api/v1/event/" + eventId,
        type: "GET",
        dataType: "json",
        async: false
    }).done(function (data) {
        // console.log(data.start.substring(0, 10) + "\u00a0\u00a0" + data.start.substring(11, 19));
        returnData = data
        document.getElementById('updateTitle').value = data.title;
        document.getElementById('updateContext').value = data.context;
        document.getElementById('updateTextColor').value = data.textColor;
        document.getElementById('updateBackgroundColor').value  = data.backgroundColor;
        document.getElementById('updateFileName').value  = data.file.split('/').reverse()[0];
    }).fail(function () {
        console.log("실패");
    });
    return returnData;
}

function deleteEvent() {
    var eventId = $('#eventId').val();
    console.log(eventId);

    $.ajax({
        url: "/event/" + eventId,
        type: "DELETE",
    }).done(function (data) {
        console.log("삭제성공");
        window.location.reload();
    }).fail(function () {
        console.log("실패");
    });
}

function updateEvent(eventId) {
    // var eventId = $('#eventId').val();
    console.log("수정 아이디 =" + eventId);

    var param = {
        'title': $("#updateTitle").val(),
        'context': $("#updateContext").val(),
        'textColor': $("#updateTextColor").val(),
        'backgroundColor': $("#updateBackgroundColor").val(),
        'userId' : $("#userId").val(),
        'allDay' : $("#allDay").val(),
        'file' : $("#updateFile").val()

    };

    var form = $('#updateForm')[0];
    var formData = new FormData(form);
    formData.append('key', new Blob([ JSON.stringify(param) ], {type : "application/json"}));
    // console.log(param.allDay);
    // if (param.loginUser === "") {
    //     return;
    // }

    $.ajax({
        url: '/event/' + eventId,
        type: 'PUT',
        enctype: 'multipart/form-data',
        contentType : false,
        processData : false,
        data: formData,
    }).done(function () {
        alert("수정완료");
        // window.location.replace("/");
    }).fail(function () {
            alert("수정실패");
            // history.back();
        }
    );
}



