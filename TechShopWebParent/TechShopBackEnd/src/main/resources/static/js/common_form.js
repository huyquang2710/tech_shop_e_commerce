$(document).ready(function () {
			$("#buttonCancel").on("click", function () {
				window.location = moduleURL;
			});
			// size image
			// show image
			$("#fileImage").change(function () {
				fileSize = this.files[0].size;
				//alert("File size: " + fileSize);

				if (fileSize > 1100000) {
					this.setCustomValidity("You must chooes an image less than 1MB!");
					this.reportValidity();
				} else {
					this.setCustomValidity("");
					showImageThumbnail(this);
				}
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

		function showModalDialog(title, message) {
			$("#modalTitle").text(title);
			$("#modalBody").text(message);
			$("#modalDialog").modal();
		}
		function showErrorModal() {
			showModalDialog("Error", message);
		}
		function showWarningModal() {
			showModalDialog("Warning", message);
		}