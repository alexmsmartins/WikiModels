function show_props(obj, obj_name) {
	  var result = "";
	  for (var i in obj)
	    result += obj_name + "." + i + " = " + obj[i] + "\n";
	  return result;
	}


/* Var Relator derived from MIT-licensed code: http://www.opensource.org/licenses/mit-license.php */
var Relator = function(Array, Object){
    // (c) Andrea Giammarchi - Mit Style License
    // (c) Alexandre Martins - Mit Style License
    if(!Array.indexOf)
        Array.indexOf = function(value){
            for(var i = 0, length = Array.length; i < length; i++)
                if(Array[i] === value)
                    return  i;
            return  -1
        };
    return  {
        get:function(value){
            return  Object[Array.indexOf(value)]
        },
        set:function(value){
            var i   = Array.indexOf(value);
            return  ~i ? Object[i] : Object[Array.push(value) - 1] = {}
        },
        del:function(value){
            var i   = Array.indexOf(value);
            if(~i){
                Array.splice(i, 1);
                Object.splice(i, 1);
            };
            return  this
        }
    }
}([], []);

print("### Relator tests ###");
Stack         = ["1",  "2",  "3"];
RelatedObject = [{}, {}, {}];

print("Stack size is "+Stack.length);
print("Add a property");
RelatedObject[Stack.indexOf("2")].description = "Number 2";

print("Remove a property");
Stack.splice(0, 1);
RelatedObject.splice(0, 1);
print("Stack size is "+Stack.length);

print("Ssituation");
Stack         = [2,                        3];
RelatedObject = [{description:"Number 2"}, {}];