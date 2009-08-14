/**
 *
 * jQuery plugin: Popup 1.0
 *
 * Copyright (c) 2008 Yves Bresson
 * www.ybresson.com
 *
 * Usage example: override defaults main_class
 *  var options = {main_class: "myClass anotherClass" }
 *  $.popup.show('My title', 'My message', options);
 *
 */
jQuery.popup = {
  
  /**
   * Show a pop-up with given title and message.
   *
   * @param title : popup title
   * @param message : popup content message
   * @param options : optional settings, can contain following params:
   *          convertNLtoBR : if true, will convert new lines (\n) to <br/> in message
   *          postDOM : function to call after creating popup elements, just before showing it (only called once)
   *          simpleAlert : if true, will use javascript's standard alert() function (auto-used if client = iPhone/iPod)
   *          main_class : class names to be added on main popup <div> tag
   *          xxx_id : id to use for popup elements, shouldn't need to modify
   * @return jQuery
   */
  show: function(title, message, options) {
  
    // define defaults and override with options if available
    settings = jQuery.extend({
      convertNLtoBR: true, 
      postDOM: function(){}, 
      simpleAlert: false, 
      main_class: "", 
      main_id: "popup", 
      bg_id: "popup_bg", 
      title_id: "popup_title", 
      msg_id: "popup_message", 
      close_id: "popup_close" 
      }, options);
  
    if(!this.initialized) {
      // inject needed elements in DOM
      domElements = '<div id="'+settings.bg_id+'"></div>';
      domElements += '<div id="'+settings.main_id+'" class="'+settings.main_class+'">';
      domElements += '<span id="'+settings.title_id+'"></span><a id="'+settings.close_id+'"> </a>';
      domElements += '<div id="'+settings.msg_id+'"></div>';
      domElements += '</div>';
      jQuery('body').append(domElements);

      // call given method after DOM has been altered (maybe user wants to attach to elements, or whatever)
      settings.postDOM();
      
      // setup event handlers
      // popup close by outer click
      jQuery('#'+settings.bg_id).click( function(){hidePopup();} );
      jQuery('#'+settings.close_id).click( function(){hidePopup();} );
    
      this.initialized = true;
    }
    
    if(!isIPhone() && !settings.simpleAlert) {
      // convert \n into <br/> if asked to (only in message param)
      if(settings.convertNLtoBR) {
        message = message.replace(/\n/g, "<br/>");
      }
      // prepare popup content
      jQuery('#'+settings.title_id).html(title);
      jQuery('#'+settings.msg_id).html(message);
      // display.. tadaaa!
      showPopup();
    } else {
      alert(message);
    }
  
    /*
     *
     * private functions (they're included right INTO the main show function)
     *
     */
  
    // show popup
    function showPopup() {
      // loads popup only if it is disabled
      if(!this.showing) {
        centerPopup();
        jQuery('#'+settings.bg_id).css({"opacity": "0"});
        jQuery('#'+settings.bg_id).fadeIn("slow");
        jQuery('#'+settings.main_id).fadeIn("slow");
        this.showing = true;
      }
    }
    
    // hide popup
    function hidePopup() {
      // disables popup only if it is enabled
      if(this.showing) {
        jQuery('#'+settings.bg_id).fadeOut("normal");
        jQuery('#'+settings.main_id).fadeOut("normal");
        this.showing = false;
      }
    }
    
    // center popup in viewport
    function centerPopup() {
      // get viewport dimensions
      var cWidth = document.documentElement.clientWidth;
      var cHeight = document.documentElement.clientHeight;
      var popupHeight = jQuery('#'+settings.main_id).height();
      var popupWidth = jQuery('#'+settings.main_id).width();
      // positionning
      jQuery('#'+settings.main_id).css({
        "top": cHeight/2-popupHeight/2, 
        "left": cWidth/2-popupWidth/2
      });
      // IE6 
      jQuery(settings.bg_id).css({"height": cHeight});
    }
    
    // detects if browser is iPhone/iPod Safari
    function isIPhone() {
    	if((navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/iPod/i))) {
    	   return true;
      }
      return false;
    }
    
    return jQuery;
  
  }, // end show function
  
  // jQuery.fn.name = function(..){...} => call $('selector').name
  // jQuery.name = function(..){...} => call $.name
  // jQuery.namespace = {name: function(..){...}, .. } => call $.namespace.name
  // inside plugin: use jQuery, not $ alias which might not exist
  
  // popup ready or not
  initialized: false,
  
  // false = disabled, true = enabled
  showing: false

};  // ';' required or will break if compressed
