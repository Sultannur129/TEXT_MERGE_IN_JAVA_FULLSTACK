/**
 * 
 */

var i = 3;
var funct;

function addTextBox() {
	
	//document.write("Metin "+i+":");
	
	var element = document.createElement("input");
	var element1 = document.createElement("a");
	var frm=document.getElementById("frm");
	element1.innerHTML="Metin "+i+":";
	element1.setAttribute("class","renk");
	element.setAttribute("id","" + (i++));
	element.setAttribute("class","txt");
	element.setAttribute("type", "text");
	element.setAttribute("value", "");
	element.setAttribute("name", "addButton");
	element1.appendChild(element);
	frm.appendChild(element1);
	

    
}






