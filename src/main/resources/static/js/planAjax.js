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

function viewPlanData(userId) {

    $.ajax({
        url: "/api/v1/plan?userId=" + userId,
        type: "GET",
        dataType: "json",
    }).done(function (planData) {
        console.log(planData);
        // document.getElementById('planViewTitle').innerText = data[0].title;
        // document.getElementById('planViewContext').innerText = data[0].context;
        var htmlOut='';
        for(var idx in planData){
            htmlOut += '<tr>';
            htmlOut += '    <td>'+(planData[idx].title)+'</td>';
            htmlOut += '    <td>'+(planData[idx].context)+'</td>';
            htmlOut += '</tr>';
        }
        $('#planViewModalBody').append(htmlOut);
    }).fail(function () {
        console.log("실패");
    });
}