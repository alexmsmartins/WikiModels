
function show_props(obj, obj_name) {
	  var result = "";
	  for (var i in obj)
	    result += obj_name + "." + i + " = " + obj[i] + "\n";
	  return result;
	}

var myDog = {
    "name" : "Spot",
    "bark" : function() { print("Woof!"); },
    "displayFullName" : function() {
        print(this.name + " The Alpha Dog");
    },
    "chaseMrPostman" : function() {
        // implementation beyond the scope of this article
    }
};
myDog.displayFullName();
myDog.bark(); // Woof!

var module1 = (function(){
    x=2;
    return {
        y: x
    }
})();
print("module.x  = " + module1);
print("module.y  = " + module1.y);

