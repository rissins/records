function planSave() {
    var param = {
        'title': $("#planTitle").val(),
        'context': $("#planContext").val(),
        'userId': $("#planUserId").val(),
    }

    $.ajax({
        url: '/plan',
        type: 'POST',
        data: param,
    }).done(function () {
        alert("계획등록 완료");
        window.location.reload();
    }).fail(function () {
            alert("실패");
        }
    );
}

function viewPlanData(userId, input) {

    $.ajax({
        url: "/api/v1/plan?userId=" + userId,
        type: "GET",
        dataType: "json",
    }).done(function (planData) {
        var htmlOut='';
        if (planData.length === 0) {
            htmlOut += '<tr>';
            htmlOut += '    <td colspan="3">' + "조회목록이 없습니다." + '</td>';
            htmlOut += '</tr>';
        } else if (input === "delete" && planData.length !== 0){
            for (var idx in planData) {
                htmlOut += '<tr>';
                htmlOut += '    <td><input type="checkbox" name="checkbox" value="'+ planData[idx].id + '"></td>';
                htmlOut += '    <td>' + (planData[idx].title) + '</td>';
                htmlOut += '    <td>' + (planData[idx].context) + '</td>';
                htmlOut += '</tr>';
            }
            document.getElementById("planDeleteButton").setAttribute('onclick', 'deleteData()');
            document.getElementById("planDeleteButton").innerText = '선택항목 삭제';

        } else if (input === 'inserts' && planData.length !== 0) {
            for (var idx in planData) {
                htmlOut += '<option>' + planData[idx].title + '</option>';
                console.log(planData[idx].title);
            }
            $('#planSelect').append(htmlOut);
        } else {
            for (var idx in planData) {
                htmlOut += '<tr>';
                htmlOut += '    <td>' + (parseInt(idx) + 1) + '</td>';
                htmlOut += '    <td>' + (planData[idx].title) + '</td>';
                htmlOut += '    <td>' + (planData[idx].context) + '</td>';
                htmlOut += '</tr>';
            }
        }
        $('#planTableBody').append(htmlOut);
    }).fail(function () {
        console.log("실패");
    });
}

function deleteData() {

    if (confirm("삭제하시겠습니까?") === false) {
        return;
    }

    var check = $("input[name=checkbox]:checked");
    var chkArray = new Array();

    $(check).each(function() { // 체크된 체크박스의 value 값을 가지고 온다.
        chkArray.push(this.value);
        console.log(this.value);
    });
    $.ajax({
        url: "/plan/" + chkArray,
        type: "DELETE",
    }).done(function () {
        window.location.reload();
    }).fail(function () {
    });
}
