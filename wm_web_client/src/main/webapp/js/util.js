//TODO: dead code,for now. Use it or delete it
// It is meant to be used to coordinate a tree-view of a mode
// land a set of accordions that contain information related to the differt
// elements of the same model


var WM = {};
WM.createEditModel = function() {
    return {
        elementStatePrototype: {
            metaId: "",
            expanded: false
        },
        pageState: function () {
            //key value dictionary that tells if the model element show be expanded or collapsed
            //The true false values are observed by the tree-view
            var expandedOrCollapsedState = {};
            var updateNodes = 2;
        }
    };
};


function show_props(obj, obj_name) {
    var i = 0;
	  var result = "";
	  for (i in obj){
              if (listeners.hasOwnProperty(o)) {
	    result += obj_name + "." + i + " = " + obj[i] + "\n";
              }
          }
	  return result;
	};

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

