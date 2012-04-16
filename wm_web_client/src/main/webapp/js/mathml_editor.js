/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

/**
 * Created with IntelliJ IDEA.
 * User: Alexandre Martins
 * Date: 03/05/12
 * Time: 00:15
 * To change this template use File | Settings | File Templates.
 */

function textAreaContentBy(id)
{
    alert("texAreaContentBy called");
    var content = $("textarea#"+id).val();
    alert("Formula in TextArea is " + content);
    return content;
}