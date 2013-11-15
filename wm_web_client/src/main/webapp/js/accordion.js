/*
 * Copyright (c) 2013. Alexandre Martins. All rights reserved.
 */
function accordionCLickHandler(event) {
    // http://api.jquery.com/event.isDefaultPrevented/
    if(event.isDefaultPrevented()) {
        return false;
    }
    $(this).toggleClass("active").next().slideToggle("slow");
    event.preventDefault();
    event.stopPropagation();
    return false; //Prevent the browser jump to the link anchor
}

jQuery(document).ready(function(){
    //Hide (Collapse) the toggle containers on load
    $(".toggle_container").hide();

    $("h3.trigger :button").click(function(e){
        e.stopPropagation();
    });

    //Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
    $("h3.trigger").click(accordionCLickHandler);
});
/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/15/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
