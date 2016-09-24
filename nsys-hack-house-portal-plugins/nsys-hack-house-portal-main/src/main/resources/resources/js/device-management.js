Nsys.$(document).ready(function () {

	function validateForm(form) {

		var formData = {
				action	: Nsys.$('#btnSubmit').val(),
				name	: Nsys.$('#name').val(),
				host	: Nsys.$('#host').val(),
				port	: Nsys.$('#port').val(),
				apiKey	: Nsys.$('#apiKey').val()
		};

		Nsys.$.ajax({
		    type: "POST",
		    url: Nsys.contextPath() + "/device/validate",
		    data: formData,
		    success: function(data) {
	    		var response = Nsys.$.parseJSON(data);

		    	Nsys.$('#errorName').hide();
		    	Nsys.$('#errorHost').hide();
		    	Nsys.$('#errorPort').hide();
		    	Nsys.$('#errorApiKey').hide();

		    	if (response.status == "success") {
		    		form.submit();

		    	} else {
		    		if (response.result.name) {
				    	Nsys.$('#errorName').text(response.result.name);
				    	Nsys.$('#errorName').show();
		    		}

		    		if (response.result.host) {
				    	Nsys.$('#errorHost').text(response.result.host);
				    	Nsys.$('#errorHost').show();
		    		}

		    		if (response.result.port) {
				    	Nsys.$('#errorPort').text(response.result.port);
				    	Nsys.$('#errorPort').show();
		    		}

		    		if (response.result.apiKey) {
				    	Nsys.$('#errorApiKey').text(response.result.apiKey);
				    	Nsys.$('#errorApiKey').show();
		    		}
		    	}
		    },
		    error: function(e) {
		    	alert('An error occurred during validating form! Error: ' + e);
		    }
		});
	}

	Nsys.bind("show.dialog", function(e, data) {
	    if (data.dialog.id == "portal-dialog") {
	    	Nsys.$('#device-edit').submit(function(e) {
	    	    e.preventDefault();
	    	    validateForm(this);
	    	});
	    }
	});

});