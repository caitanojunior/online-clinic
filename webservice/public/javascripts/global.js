// Clientlist data array for filling in info box
var clientListData = [];

// DOM Ready =============================================================
$(document).ready(function() {

    // Populate the client table on initial page load
    populateTable();

});

// Functions =============================================================

// funçao que preenche a tabela com dados do banco
function populateTable() {

    // Empty content string
    var tableContent = '';

    // jQuery AJAX call for JSON
    $.getJSON( '/clients/clientlist', function( data ) {
		
		// Stick our client data array into a clientlist variable in the global object
		clientListData = data;
		
        // For each item in our JSON, add a table row and cells to the content string
        $.each(data, function(){
            tableContent += '<tr>';
            tableContent += '<td><a href="#" class="linkshowclient" rel="' + this.fullname + '">' + this.fullname + '</a></td>';									
            tableContent += '<td>' + this.phone + '</td>';
            tableContent += '<td>' + this.healthPlanNumber + '</td>';
            tableContent += '<td>' + this.healthPlan + '</td>';
            tableContent += '<td>' + this.schedulingDate + ' ' + '<b>' + this.schedulingHour + '</b>'+ '</td>';
            tableContent += '<td><a href="#" class="linkdeleteclient" rel="' + this._id + '">Cancel</a></td>';
            tableContent += '</tr>';

        });

        // Inject the whole content string into our existing HTML table
        $('#clientList table tbody').html(tableContent);
    }); 
};

// Info function that shows the selected customer
	function showClientInfo(event) {

		// Prevent Link from Firing
		event.preventDefault();

		// Retrieve fullname from link rel attribute
		var thisClientName = $(this).attr('rel');

		// Get Index of object based on id value
		var arrayPosition = clientListData.map(function(arrayItem) { return arrayItem.fullname; }).indexOf(thisClientName);
		
		// Get our Client Object
		var thisClientObject = clientListData[arrayPosition];


		//Populate Info Box
        $('#healthPlan').text(thisClientObject.healthPlan);
        $('#healthPlanNumber').text(thisClientObject.healthPlanNumber);
		$('#clientInfoName').text(thisClientObject.fullname);
		$('#clientInfoPhone').text(thisClientObject.phone);
		$('#clientInfoSchedulingDate').text(thisClientObject.schedulingDate);
        $('#clientInfoSchedulingHour').text(thisClientObject.schedulingHour);

	};

	// Add Client
function addClient(event) {
    event.preventDefault();

    // Super basic validation - increase errorCount variable if any fields are blank
    var errorCount = 0;
    $('#addClient input').each(function(index, val) {
        if($(this).val() === '') { errorCount++; }
    });

    // Check and make sure errorCount's still at zero
    if(errorCount === 0) {

        // If it is, compile all client info into one object
        var newClient = {
            'fullname': $('#addClient fieldset input#inputClientFullname').val(),
            'phone': $('#addClient fieldset input#inputClientPhone').val(),
            'healthPlan' : $('#addClient #inputClientHealthPlan').val(),
            'healthPlanNumber' : $('#addClient fieldset input#inputClientHealthPlanNumber').val(),
            'schedulingDate': $('#addClient fieldset input#inputClientSchedulingDate').val(),
            'schedulingHour': $('#addClient fieldset input#inputClientSchedulingHour').val()
        }

        // Use AJAX to post thisClientObject object to our addclient service
        $.ajax({
            type: 'POST',
            data: newClient,
            url: '/clients/addclient',
            dataType: 'JSON'
        }).done(function( response ) {

            // Check for successful (blank) response
            if (response.msg === '') {

                // Clear the form inputs
                $('#addClient fieldset input').val('');

                // Update the table
                populateTable();

            }
            else {

                // If something goes wrong, alert the error message that our service returned
                alert('Error: ' + response.msg);

            }
        });
    }
    else {
        // If errorCount is more than 0, error out
        alert('Please fill in all fields!');
        return false;
    }
};

// Get Client
function getClient(event) {
    event.preventDefault();

        id = document.getElementById('clientInfoId').innerHTML;
        name = document.getElementById('clientInfoName').innerHTML;
        phone = document.getElementById('clientInfoPhone').innerHTML;
        schedulingDate = document.getElementById('clientInfoSchedulingDate').innerHTML;
        schedulingHour = document.getElementById('clientInfoSchedulingHour').innerHTML;

        $('#addClient fieldset #inputClientFullname').val(name);
        $('#addClient fieldset #inputClientPhone').val(phone);
        $('#addClient fieldset #inputClientSchedulingDate').val(schedulingDate);
        $('#addClient fieldset #inputClientSchedulingHour').val(schedulingHour);

};


//Edit Client
function editClient(){
    event.preventDefault();

    // Pop up a confirmation dialog
    var confirmation = confirm('Are you sure you want to edit this scheduling?');

    // Check and make sure the client confirmed
    if (confirmation === true) {

        var clientUpdated  = {
            '_id': id,
            'fullname': $('#addClient fieldset #inputClientFullname').val(),
            'phone':  $('#addClient fieldset #inputClientPhone').val(),
            'schedulingDate': $('#addClient fieldset #inputClientSchedulingDate').val(),
            'schedulingHour': $('#addClient fieldset #inputClientSchedulingHour').val()
        }

        $.ajax({
            type:'post',
            data:clientUpdated,
            dataType:'json',
            url: '/clients/updateclient/' + id
        }).done(function( response ) {

            // Check for a successful (blank) response
            if (response.msg === '') {
                // Clear the form inputs
                $('#addClient fieldset input').val('');

                // Update the table
                populateTable();
            }
            else {
                alert('Error: ' + response.msg);
            }
        });
    }
    else {

        // If they said no to the confirm, do nothing
        return false;

    }
};

// Delete Client
function deleteClient(event) {

    event.preventDefault();

    // Pop up a confirmation dialog
    var confirmation = confirm('Are you sure you want to cancel this scheduling?');

    // Check and make sure the client confirmed
    if (confirmation === true) {

        // If they did, do our delete
        $.ajax({
            type: 'DELETE',
            url: '/clients/deleteclient/' + $(this).attr('rel')
        }).done(function( response ) {

            // Check for a successful (blank) response
            if (response.msg === '') {
            }
            else {
                alert('Error: ' + response.msg);
            }

            // Update the table
            populateTable();

        });

    }
    else {

        // If they said no to the confirm, do nothing
        return false;

    }

};

	
	// Fullname link click
    $('#clientList table tbody').on('click', 'td a.linkshowclient', showClientInfo);

    // Add Client button click
    $('#btnAddClient').on('click', addClient);

    // Delete Client link click
    $('#clientList table tbody').on('click', 'td a.linkdeleteclient', deleteClient);

    // Get Client button click
    $('#btnEdit').on('click', getClient);

    // Edit Client button click
    $('#btnEditClient').on('click', editClient);


