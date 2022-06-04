var extraImageCount = 0;

$(document).ready(function () {

	$("input[name='extraImage']").each(function (index) {
		extraImageCount++;
		$(this).change(function () {
			if (!checkFileSize(this)) {
				return;
			}
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

	if (index >= extraImageCount - 1) {
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