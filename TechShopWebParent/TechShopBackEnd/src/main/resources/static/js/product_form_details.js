function addNextDetailSection() {
	
	allDivDetails = $("[id^='divDetail']");
	divDetailCount = allDivDetails .length;
	
    htmlDetailSection = `
         <div class="form-inline" id="divDetail${divDetailCount}">
			<label for="" class="m-3">Name</label>
			<input type="text" class="form-control w-25" name="detailNames" maxlength="255">
			<label for="" class="m-3">Value</label>
			<input type="text" class="form-control w-25" name="detailNames" maxlength="255">
		</div>
    `;

    $("#divProductDetails").append(htmlDetailSection);
    
    previousDivDetailSection = allDivDetails.last();
    previousDivDetailID = previousDivDetailSection.attr("id");
    
   htmlLinkRemove = `
		<a class="btn fas fa-times-circle fa-2x icon-dark float-right" 
		href="javascript:removeDetailSectionById('${previousDivDetailID}')"
		title="Remove this image"
		</a>
		`;
		
	previousDivDetailSection.append(htmlLinkRemove);
}

function removeDetailSectionById(id) {
	$("#" + id).remove();
}