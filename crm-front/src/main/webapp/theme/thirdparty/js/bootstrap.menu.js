/**
 * Created by Luck on 2015/11/12.
 */
$.fn.bootstrapMenu = function (option) {

    this.each(function () {
        var $this = this;
        if (option.url == null) {
            alert("url is null");
        } else {
            $.getJSON(option.url, function (data) {
                for (var i = 0; i < data.length; i++) {

                    var menudiv = document.createElement("div");
                    var menutitlediv = document.createElement("div");
                    menutitlediv.setAttribute("class", "list-group-item list-group-item-success");
                    menudiv.appendChild(menutitlediv);
                    var menuitemlistdiv = document.createElement("div");
                    menudiv.appendChild(menuitemlistdiv);
                    menuitemlistdiv.setAttribute("class", "menuitemlist" + i);
                    $(menutitlediv).click(function () {
                        $(this.nextSibling).slideToggle();
                    });
                    menutitlediv.appendChild(document.createTextNode(data[i]['menu']));
                    for (var j = 0; j < data[i].menuitem.length; j++) {

                        var menuitemdiv = document.createElement("div");
                        menuitemdiv.setAttribute("class", "list-group-item");
                        menuitemlistdiv.appendChild(menuitemdiv);
                        menuitemdiv.appendChild(document.createTextNode(data[i].menuitem[j]));

                    }
                    $this.appendChild(menudiv);


                }

            })
        }


    })
};



function assemblyTreeObj(sNodes, option){
    var r = [];
    var tmpMap = [];
    option = $.extend({key:"id", parentKey: "pId", childsKey: "childs"}, option);
    var key = option.key;
    var parentKey = option.parentKey;
    var childsKey = option.childsKey;
    for (i=0, l=sNodes.length; i<l; i++) {
        tmpMap[sNodes[i][key]] = sNodes[i];
    }
    for (i=0, l=sNodes.length; i<l; i++) {
        if (tmpMap[sNodes[i][parentKey]]) {
            if (!tmpMap[sNodes[i][parentKey]][childsKey])
                tmpMap[sNodes[i][parentKey]][childsKey] = [];
            tmpMap[sNodes[i][parentKey]][childsKey].push(sNodes[i]);
        } else {
            r.push(sNodes[i]);
        }
    }
    console.dir(r);
    return r;
}



$.fn.zwfrMenu = function (option) {

    var $this = this;
    if (option.url == null) {
        alert("url is null");
    } else {
        $.getJSON(option.url, function (data) {
            a=assemblyTreeObj(data);
            for (i = 0; i < a.length; i++) {

                var menudiv = document.createElement("div");
                var menutitlediv = document.createElement("div");
                menutitlediv.setAttribute("class", "list-group-item list-group-item-success");
                menudiv.appendChild(menutitlediv);
                var menuitemlistdiv = document.createElement("div");
                menudiv.appendChild(menuitemlistdiv);
                menuitemlistdiv.setAttribute("class", "menuitemlist" + i);
                $(menutitlediv).click(function () {
                    $(this.nextSibling).slideToggle();
                });
                menutitlediv.appendChild(document.createTextNode(a[i]['name']));
                if (a[i].childs) {
                    for (var j = 0; j < a[i].childs.length; j++) {

                        var menuitemdiv = document.createElement("div");
                        menuitemdiv.setAttribute("class", "list-group-item");
                        menuitemlistdiv.appendChild(menuitemdiv);
                        menuitemdiv.appendChild(document.createTextNode(a[i].childs[j]['name']));

                    }
                }
                console.dir($this);
                $this.append(menudiv);



            }






        })
    }




};



$.fn.bootstrapItemlist = function (option) {

    this.each(function () {
        var $this = this;
        if (option.url == null) {
            alert("url is null");
        } else {
            $.getJSON(option.url, function (data) {
                for (var i = 0; i < data.length; i++) {

                    var itemdiv=document.createElement("div");  // 通过 DOM 创建新元素

                    itemdiv.setAttribute("class","col-xs-6 col-sm-4 col-md-3");
                    var itema=document.createElement("a");
                    itema.setAttribute("href",data[i].href);
                    var itemimg=document.createElement("img");
                    var textdiv=document.createElement("div");// 通过 DOM 创建新元素
                    var namep=document.createElement("p");
                    namep.setAttribute("class","text-center");
                    var pricep=document.createElement("p");
                    pricep.setAttribute("class","text-center");
                    itemimg.setAttribute("src",data[i].imgsrc);
                    var namepText = document.createTextNode(data[i].name);
                    namep.appendChild(namepText);
                    var pricepText = document.createTextNode(data[i].price);
                    pricep.appendChild(pricepText);


                    itemdiv.appendChild(itema);
                    itemdiv.appendChild(textdiv);
                    itema.appendChild(itemimg);
                    textdiv.appendChild(namep);
                    textdiv.appendChild(pricep);


                    $this.appendChild(itemdiv);


                }


            })
        }


    })
};
