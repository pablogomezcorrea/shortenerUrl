// Hide the rwTable component
$("document").ready(function() {
	$("#rwTable").hide();
	
	$("#url").keypress(function(e){		
		if(e.which == 13) {
			$("#btnForm").click();
		}
	});
	
});

// Send data to controller
$(document).on("click", "#btnForm", function() {
	data = {"url": $("#url").val()}
	
	$.post("/form", data, function(data){
		if (data.urlOri != undefined) {
			$("thead").show();
			$("#tblShortUrl").append(`
				<tbody>
					<tr>
						<td>${data.urlOri}</td>
						<td><a href="${data.urlMin}">${data.urlMin}</a></td>
					</tr>
				</tbody>
			`);
			$("#rwTable").show();
		} else {
			M.toast({html: "Invalid url format !"});
		}
	});
});

$(document).on("click", "#btnCleanResult", function() {
	$("thead").hide();
	$("tbody").remove();
	$("#url").val("");
});