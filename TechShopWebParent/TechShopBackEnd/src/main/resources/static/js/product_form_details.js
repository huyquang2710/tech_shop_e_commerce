function addNextDetailSection() {
    htmlDetailSection = `
         <div class="form-inline">
			<label for="" class="m-3">Name</label>
			<input type="text" class="form-control w-25" name="detailNames" maxlength="255">
			<label for="" class="m-3">Value</label>
			<input type="text" class="form-control w-25" name="detailNames" maxlength="255">
		</div>
    `;

    $("#divProductDetails").append(htmlDetailSection);
}