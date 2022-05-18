$(document).ready(function () {
	$("#buttonCancel").on("click", function () {
		window.location = moduleURL;
	});
	// size image
	// show image
	$("#fileImage").change(function () {
		if (!checkFileSize(this)) {
			return;
		}
		showImageThumbnail(this);
	});
});

// show image

function showImageThumbnail(fileInput) {
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function (e) {
		$("#thumbnail").attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
}

function checkFileSize(fileInput) {
	fileSize = fileInput.files[0].size;
	//alert("File size: " + fileSize);

	if (fileSize > 50240000) {
		fileInput.setCustomValidity("You must chooes an image less than 1MB!");
		fileInput.reportValidity();
		return false;
	} else {
		fileInput.setCustomValidity("");
		return true;
	}
}

function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}
function showErrorModal(message) {
	showModalDialog("Error", message);
}
function showWarningModal(message) {
	showModalDialog("Warning", message);
}