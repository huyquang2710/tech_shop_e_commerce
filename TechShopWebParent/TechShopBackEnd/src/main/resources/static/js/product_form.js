var extraImageCount = 0;
dropdownBrands = $("#brand");
dropdownCategories = $("#category");

$(document).ready(function () {

	$("#shortDescription").richText();
	$("#fullDescription").richText();

	dropdownBrands.change(function () {
		dropdownCategories.empty();
		getCategories();
	});
	getCategories();
	
	$("input[name='extraImage']").each(function(index) {
		extraImageCount++;
		$(this).change(function() {
			
			showExtraImageThumbnail(this, index);
		});
	});
});

function showExtraImageThumbnail(fileInput, index) {
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function (e) {
		$("#extraThumbnail" + index).attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
	
	if(index >= extraImageCount - 1 ) {
		addNextExtraImageSection(index + 1);
	}
}

function addNextExtraImageSection(index) {
	htmlExtraImage = `
		<div class="col border m-3 p-2" id="divExtraImages${index}">
			<div id="extraImageHeader${index}"><label for="">Extra Image #${index + 1}: </label></div>
			<div class="m-2">
				<img id="extraThumbnail${index}" src="${defaultImageThumbnailSrc}" alt="Extra image #${index + 1}" class="img-fluid" >
			</div>
			<div class="">
				<input type="file" 
				onchange="showExtraImageThumbnail(this, ${index})"
				name="extraImage"
				 accept="image/png, image/jpeg" />
			</div>
		</div>`
		;
		
	htmlLinkRemove = `
		<a class="btn fas fa-times-circle fa-2x icon-dark float-right" 
		title="Remove this image"
		href="javascript:removeExtraImage(${index} - 1)" >
		</a>
		`;	
	$("#divProductImages").append(htmlExtraImage);
	$("#extraImageHeader" + (index - 1)).append(htmlLinkRemove);
}

function removeExtraImage(index) {
	$("#divExtraImages" + index).remove();;
}


function getCategories() {
	brandId = dropdownBrands.val();
	url = moduleURLBrands + "/" + brandId + "/categories";

	$.get(url, function (responseJson) {
		$.each(responseJson, function (index, category) {
			$("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
		});
	});
}

function checkUnique(form) {
	url = "[[@{/products/check_unique}]]";
	productId = $("#id").val();
	productName = $("#name").val();

	csrfValue = $("input[name='_csrf']").val();
	params = { id: productId, name: productName, _csrf: csrfValue };

	$.post(url, params, function (response) {
		if (response == "OK") {
			form.submit();
		} else if (response == "Duplicate") {
			showModalDialog("Warning", "There is another product having the mail: " + productName);
		} else {
			showModalDialog("Error", "Unknown response from server");
		}
	}).fail(function () {
		showModalDialog("Error", "Could not connect to the server");
	});

	return false;
}
