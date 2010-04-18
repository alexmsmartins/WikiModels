/*
 * SimpleSecurityContext.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.security

class SimpleSecurityContext extends SecurityContext{
    //initialize regualr expressions
    val allMethods_reg = """(GET|POST|PUT|DELETE)"""
    val allButRead_reg = """(POST|PUT|DELETE)"""
    val model_reg = """models?(/\S*)?"""
    val allRes_reg = """\S+"""

    /**
     * method that ckecks user permissions according to the folowing rules:
     *  - "admin" is authorized to do everything to every resource
     *  - "anonymous" can only read resources that do not repate to user
     *  information
     *  - all other users are atuthrized to do anything to models their personal
     *  information
     */
    def isAuthorizedTo(user:String , method:String, resource:String):Boolean = {
        //TODO change isAuthorizedTo to use match case if
        //the called resource is checked with regular expressions
        //TODO Incomplete rule -> users can change their own information with the exception of their username

        if(user == "admin"){
            true
        } else
        if(user == "anonymous" ) {
            if(  java.util.regex.Pattern.matches( model_reg, resource)
               && method == "GET") {
                true
            } else {
                false
            }
        } else 
        if(resource.startsWith("user")){
            if(resource == "user/" + user){
                true
            } else {
                false
            }
        } else {
            true
        }
        
        //TODO - implemnt an RBAC lke permission system.
    }
}
