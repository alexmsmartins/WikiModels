FCKConfig.AutoDetectLanguage = false ;
FCKConfig.DefaultLanguage = "en-uk" ;
FCKConfig.ToolbarSets["Default"] = [
['Cut','Copy','Paste','PasteText','PasteWord'],['Undo','Redo','-','Find','Replace'],
['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
['OrderedList','UnorderedList','-','Outdent','Indent','Blockquote'],
['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],['Link','Unlink'],
['Image','Table','Rule','SpecialChar'],['FontSize','TextColor','BGColor'],['Source','FitWindow']
] ;



/*var idFD =1, idC=1, idS=1, idP=1, idIA=1, idCt=1, idR = 1;

function AddFunctionDefinition() {
    // THis is the <ul id="function_def"> element that will contains the new elements
	var container = document.getElementById('function_def');

	// Create a new <li> element for to insert inside <ul id="function_def">
	var new_FD = document.createElement('li');
    var new_FDNote = document.createElement('li');
    
	new_FD.innerHTML = "<createDescription:function_definition />"
    //new_FD.innerHTML = "<span class='file'><h3>Function Definition #"+idFD+":</h3><br />\n\
//<textarea id='functionDefinition"+idFD+"' type='textarea' rows ='3' cols='100' maxlength='10000' /></span>";
	new_FDNote.innerHTML = "<span class='file'><h3>Function Definition #"+idFD+" Note:</h3><br />\n\
<textarea id='functionDefinitionNote"+idFD+"' type='textarea' rows ='10' cols='120' maxlength='50000' /></span>";
	container.insertBefore(new_FDNote, container.firstChild);
    container.insertBefore(new_FD, container.firstChild);
    idFD++;
}

function AddCompartment() {
    // THis is the <ul id="compart"> element that will contains the new elements
	var container = document.getElementById('compart');

	// Create a new <li> element for to insert inside <ul id="compart">
	var new_FD = document.createElement('li');
    var new_FDNote = document.createElement('li');

	new_FD.innerHTML = "<span class='file'><h3>Compartment #"+idC+":</h3><br />\n\
<textarea id='compartment"+idC+"' type='textarea' rows ='3' cols='100' maxlength='10000' /></span>";
	new_FDNote.innerHTML = "<span class='file'><h3>Compartment #"+idC+" Note:</h3><br />\n\
<textarea id='compartment"+idC+"' type='textarea' rows ='10' cols='120' maxlength='50000' /></span>";
	container.insertBefore(new_FDNote, container.firstChild);
    container.insertBefore(new_FD, container.firstChild);
    idC++;
}

function AddSpecies() {
    // THis is the <ul id="specie"> element that will contains the new elements
	var container = document.getElementById('specie');

	// Create a new <li> element for to insert inside <ul id="specie">
	var new_FD = document.createElement('li');
    var new_FDNote = document.createElement('li');

	new_FD.innerHTML = "<span class='file'><h3>Specie #"+idS+":</h3><br />\n\
<textarea id='specie"+idS+"' type='textarea' rows ='3' cols='100' maxlength='10000' /></span>";
	new_FDNote.innerHTML = "<span class='file'><h3>Specie #"+idS+" Note:</h3><br />\n\
<textarea id='specie"+idS+"' type='textarea' rows ='10' cols='120' maxlength='50000' /></span>";
	container.insertBefore(new_FDNote, container.firstChild);
    container.insertBefore(new_FD, container.firstChild);
    idS++;
}

function AddParameter() {
    // THis is the <ul id="paramet"> element that will contains the new elements
	var container = document.getElementById('paramet');

	// Create a new <li> element for to insert inside <ul id="paramet">
	var new_FD = document.createElement('li');
    var new_FDNote = document.createElement('li');

	new_FD.innerHTML = "<span class='file'><h3>Parameter #"+idP+":</h3><br />\n\
<textarea id='parameter"+idP+"' type='textarea' rows ='3' cols='100' maxlength='10000' /></span>";
	new_FDNote.innerHTML = "<span class='file'><h3>Parameter #"+idP+" Note:</h3><br />\n\
<textarea id='parameter"+idP+"' type='textarea' rows ='10' cols='120' maxlength='50000' /></span>";
	container.insertBefore(new_FDNote, container.firstChild);
    container.insertBefore(new_FD, container.firstChild);
    idP++;
}

function AddInitialAssignment() {
    // THis is the <ul id="initialAssig"> element that will contains the new elements
	var container = document.getElementById('initialAssig');

	// Create a new <li> element for to insert inside <ul id="initialAssig">
	var new_FD = document.createElement('li');
    var new_FDNote = document.createElement('li');

	new_FD.innerHTML = "<span class='file'><h3>Initial Assignment #"+idIA+":</h3><br />\n\
<textarea id='initialAssignment"+idIA+"' type='textarea' rows ='3' cols='100' maxlength='10000' /></span>";
	new_FDNote.innerHTML = "<span class='file'><h3>Initial Assignment #"+idIA+" Note:</h3><br />\n\
<textarea id='initialAssignment"+idIA+"' type='textarea' rows ='10' cols='120' maxlength='50000' /></span>";
	container.insertBefore(new_FDNote, container.firstChild);
    container.insertBefore(new_FD, container.firstChild);
    idIA++;
}

function AddConstraint() {
    // THis is the <ul id="const"> element that will contains the new elements
	var container = document.getElementById('const');

	// Create a new <li> element for to insert inside <ul id="const">
	var new_FD = document.createElement('li');
    var new_FDNote = document.createElement('li');

	new_FD.innerHTML = "<span class='file'><h3>Constraint #"+idCt+":</h3><br />\n\
<textarea id='constraint"+idCt+"' type='textarea' rows ='3' cols='100' maxlength='10000' /></span>";
	new_FDNote.innerHTML = "<span class='file'><h3>Constraint #"+idCt+" Note:</h3><br />\n\
<textarea id='constraint"+idCt+"' type='textarea' rows ='10' cols='120' maxlength='50000' /></span>";
	container.insertBefore(new_FDNote, container.firstChild);
    container.insertBefore(new_FD, container.firstChild);
    idCt++;
}

function AddReaction() {
    // THis is the <ul id="react"> element that will contains the new elements
	var container = document.getElementById('react');

	// Create a new <li> element for to insert inside <ul id="react">
	var new_FD = document.createElement('li');
    var new_FDNote = document.createElement('li');

	new_FD.innerHTML = "<span class='file'><h3>Reaction #"+idR+":</h3><br />\n\
<textarea id='reaction"+idR+"' type='textarea' rows ='3' cols='100' maxlength='10000' /></span>";
	new_FDNote.innerHTML = "<span class='file'><h3>Reaction #"+idR+" Note:</h3><br />\n\
<textarea id='reaction"+idR+"' type='textarea' rows ='10' cols='120' maxlength='50000' /></span>";
	container.insertBefore(new_FDNote, container.firstChild);
    container.insertBefore(new_FD, container.firstChild);
    idR++;
}

    /*
     $(document).ready(function(){
	$("#model_tree").treeview();
	$("#add").click(function() {
		var branches = $("<li><span class='folder'>New Sublist</span><ul>" +
			"<li><span class='file'>Item1</span></li>" +
			"<li><span class='file'>Item2</span></li></ul></li>").appendTo("#model_tree");
		$("#modeltree").treeview({
			add: branches
		});
		branches = $("<li class='closed'><span class='folder'>New Sublist</span><ul><li><span class='file'>Item1</span></li><li><span class='file'>Item2</span></li></ul></li>").prependTo("#function_def");
		$("#model_tree").treeview({
			add: branches
		});
	});
});
     *
     *
     *
     *
     *$("#model_tree").treeview();
    $("#add").click(function() {
        /*var branches = $("<script type='text/javascript'>var oFCKeditor02 = new FCKeditor('functionDefinitionArea');\n\
            oFCKeditor02.BasePath = '../classpath/js/fckeditor/'; oFCKeditor02.Create();</script>" +
			"<li><span class='file'><createDescription:function_definition /></span></li>" +
			"<li><span class='file'><createDescription:function_definition_note /></span></li>").appendTo("#model_tree");
		$("#model_tree").treeview({
			add: branches
		});
        var script = $("<script type='text/javascript'>var oFCKeditor02 = new FCKeditor('functionDefinitionArea');\n\
            oFCKeditor02.BasePath = '../classpath/js/fckeditor/'; oFCKeditor02.Create();</script>")
		branches = $(script + "<li><span class='file'><createDescription:function_definition /></span></li>\n\
<li><span class='file'><createDescription:function_definition_note /></span></li>").prependTo("#function_def");
		$("#model_tree").treeview({
			add: branches
		});
        var branches = $("<li><span class='folder'>New Sublist</span><ul>" +
			"<li><span class='file'>Item1</span></li>" +
			"<li><span class='file'>Item2</span></li></ul></li>").appendTo("#model_tree");
		$("#model_tree").treeview({
			add: branches
		});
		branches = $("<li class='closed'><span class='folder'>New Sublist</span><ul><li><span class='file'>Item1</span></li><li><span class='file'>Item2</span></li></ul></li>").prependTo("#function_def");
		$("#model_tree").treeview({
			add: branches
		});
	});
    $(document).write("entrei aqui");

	$("#model_tree").treeview({
        animated: "slow",
		control: "#treecontrol",
		persist: "cookie",
		cookieId: "filetree"
	});

    $(document).write("entrei aqui");
});*/