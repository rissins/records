function eventSave() {
    var planId = $('#planSelect option:selected').val();
    var plan = readData(planId);
    var param = {
        'title': $("#title").val(),
        'context': $("#context").val(),
        'textColor': $("#textColor").val(),
        'backgroundColor': $("#backgroundColor").val(),
        'userId': $("#userId").val(),
        'allDay': $("#allDay").val(),
        'plan': plan,
    }
    var form = $('#uploadForm')[0];
    var formData = new FormData(form);
    formData.append('key', new Blob([JSON.stringify(param)], {type: "application/json"}));

    if (!$('#file').val()) {
        alert("파일을 첨부해주세요.")
        return;
    }

    if (confirm("등록하시겠습니까?") === false) {
        return;
    }
    $("#insertModal").modal('hide');

    $.ajax({
        url: '/event',
        type: 'POST',
        enctype: 'multipart/form-data',
        contentType: false,
        processData: false,
        data: formData,
    }).done(function () {
        window.location.reload();
    }).fail(function () {
        alert("등록 실패");
    }
    );
}


function searchData() {
    let returnValue;

    $.ajax({
        url: "/event/" + $("#userId").val(),
        type: "GET",
        dataType: "json",
        async: false,

    }).done(function (data) {
        console.log("성공");
        returnValue = data;

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
        // console.log(data.start.substring(0, 10) + "\u00a0\u00a0" + data.start.substring(11, 19));
        console.log(data);
        document.getElementById('eventId').value = eventId;
        document.getElementById('viewTitle').innerText = "[" + data.plan.title + "]  " + data.title;
        document.getElementById('viewContext').innerText = data.context;
        document.getElementById('viewFile').src = data.file;
        document.getElementById('uploadDate').innerText = data.start.substring(0, 10) + "\u00a0\u00a0" + data.start.substring(11, 19);
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
        returnData = data
        document.getElementById('updateTitle').value = data.title;
        document.getElementById('updateContext').value = data.context;
        document.getElementById('updateTextColor').value = data.textColor;
        document.getElementById('updateBackgroundColor').value = data.backgroundColor;
    }).fail(function () {
        console.log("실패");
    });
    return returnData;
}

function deleteEvent() {
    var eventId = $('#eventId').val();

    if (sessionId === "") {
        alert("삭제 할 수 없는 이벤트입니다.");
        return;
    }

    if (confirm("삭제하시겠습니까?") === false) {
        return;
    }

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
    console.log("수정 아이디 =" + eventId);
    if (sessionId === "") {
        alert("수정 할 수 없는 이벤트입니다.");
        return;
    }

    var param = {
        'title': $("#updateTitle").val(),
        'context': $("#updateContext").val(),
        'textColor': $("#updateTextColor").val(),
        'backgroundColor': $("#updateBackgroundColor").val(),
        'userId': $("#updateUserId").val(),
        'allDay': $("#allDay").val(),
    };

    var form = $('#updateForm')[0];
    var formData = new FormData(form);
    formData.append('key', new Blob([JSON.stringify(param)], {type: "application/json"}));

    console.log(formData);
    $.ajax({
        url: '/event/' + eventId,
        type: 'PUT',
        enctype: 'multipart/form-data',
        contentType: false,
        processData: false,
        data: formData,
    }).done(function () {
            alert("수정완료");
            window.location.reload();
    }).fail(function () {
            alert("수정실패");
        }
    );
}



