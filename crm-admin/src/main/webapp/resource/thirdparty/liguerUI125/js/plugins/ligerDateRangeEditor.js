/**
* jQuery ligerUI 1.2.4
* 
* http://ligerui.com
*  
* Author daomi 2014 [ gd_star@163.com ] 
* 
*/
(function ($)
{
    $.fn.ligerDateRangeEditor = function ()
    {
        return $.ligerui.run.call(this, "ligerDateRangeEditor", arguments);
    };

    $.fn.ligerGetDateRangeEditorManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetDateRangeEditorManager", arguments);
    };
    $.ligerDefaults.DateRangeEditor = {
		minDate: undefined,
		maxDate: undefined,
        dateFmt: "yyyy-MM-dd",
        width : null,
        onChangeDate: false,
        cancelable: true,      //可取消选择
        readonly: false              //是否只读
    };
// $.ligerDefaults.DateRangeEditorString = {
//        dayMessage: ["日", "一", "二", "三", "四", "五", "六"],
//        monthMessage: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
//        todayMessage: "今天",
//        closeMessage: "关闭"
//  };
    $.ligerMethos.DateRangeEditor = {};

    $.ligerui.controls.DateRangeEditor = function (element, options)
    {
        $.ligerui.controls.DateRangeEditor.base.constructor.call(this, element, options);
    };
    $.ligerui.controls.DateRangeEditor.ligerExtend($.ligerui.controls.Input, {
        __getType: function ()
        {
            return 'DateRangeEditor';
        },
        __idPrev: function ()
        {
            return 'DateRangeEditor';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.DateRangeEditor;
        },
        _render: function ()
        {
            var g = this, p = this.options;
            if (this.element.tagName.toLowerCase() != "input" || this.element.type != "text")
                return;
            g.inputText = $(this.element);
            if (!g.inputText.hasClass("l-text-field"))
                g.inputText.addClass("l-text-field");
            if (!g.inputText.hasClass("WdatePicker"))
                g.inputText.addClass("WdatePicker");
            g.link = $('<div class="l-trigger"><div class="l-trigger-icon"></div></div>');
            g.text = g.inputText.wrap('<div class="l-text l-text-date"></div>').parent();
            g.text.append('<div class="l-text-l"></div><div class="l-text-r"></div>');
            g.text.append(g.link);
            //添加个包裹
            g.textwrapper = g.text.wrap('<div class="l-text-wrapper"></div>').parent();
            //设置主体
            g.bulidContent();
            //初始化   
            //初始值
            if (p.initValue)
            {
                g.inputText.val(p.initValue);
            }
            if (g.inputText.val() != "")
            {
                g.onTextChange();
            }
            /**************
            **bulid evens**
            *************/
            //toggle even
            //不可用属性时处理
            if (p.disabled) {
                g.inputText.attr("readonly", "readonly");
                g.text.addClass('l-text-disabled');
            }
            //文本框
            g.inputText.change(function ()
            {
                g.onTextChange();
            }).blur(function ()
            {
                g.text.removeClass("l-text-focus");
            }).focus(function ()
            {
                g.text.addClass("l-text-focus");
            });
            g.text.hover(function ()
            {
                g.text.addClass("l-text-over");
            }, function ()
            {
                g.text.removeClass("l-text-over");
            });
            //LEABEL 支持
            if (p.label)
            {
                g.labelwrapper = g.textwrapper.wrap('<div class="l-labeltext"></div>').parent();
                g.labelwrapper.prepend('<div class="l-text-label" style="float:left;display:inline;">' + p.label + ':&nbsp</div>');
                g.textwrapper.css('float', 'left');
                if (!p.labelWidth)
                {
                    p.labelWidth = $('.l-text-label', g.labelwrapper).outerWidth();
                } else
                {
                    $('.l-text-label', g.labelwrapper).outerWidth(p.labelWidth);
                }
                $('.l-text-label', g.labelwrapper).width(p.labelWidth);
                $('.l-text-label', g.labelwrapper).height(g.text.height());
                g.labelwrapper.append('<br style="clear:both;" />');
                if (p.labelAlign)
                {
                    $('.l-text-label', g.labelwrapper).css('text-align', p.labelAlign);
                }
                g.textwrapper.css({ display: 'inline' });
                g.labelwrapper.width(g.text.outerWidth() + p.labelWidth + 2);
            }

            g.set(p);
        },
        destroy: function ()
        {
            if (this.textwrapper) this.textwrapper.remove();
            if (this.dateeditor) this.dateeditor.remove();
            this.options = null;
            $.ligerui.remove(this);
        },
        bulidContent: function ()
        {
            var g = this, p = this.options;
            p.onpicked = g.inputText.onkeyup;
            g.inputText.bind("focus", function(){
				//var datePickerOptions = {
//					onpicked: function(){g.keyup()},
//					dateFmt: data.dateFmt == undefined ? "yyyy-MM-dd" : data.dateFmt,
//					minDate: data.minDate == undefined ? "" : data.minDate,
//					maxDate: data.maxDate == undefined ? "" :data.maxDate
//				};
				new WdatePicker(p);
			});
        },
        updateSelectBoxPosition: function ()
        {
            var g = this, p = this.options;
            if (p.absolute)
            {
                var contentHeight = $(document).height();
                if (Number(g.text.offset().top + 1 + g.text.outerHeight() + g.dateeditor.height()) > contentHeight
            			&& contentHeight > Number(g.dateeditor.height() + 1))
                {
                    //若下拉框大小超过当前document下边框,且当前document上留白大于下拉内容高度,下拉内容向上展现
                    g.dateeditor.css({ left: g.text.offset().left, top: g.text.offset().top - 1 - g.dateeditor.height() });
                } else
                {
                    g.dateeditor.css({ left: g.text.offset().left, top: g.text.offset().top + 1 + g.text.outerHeight() });
                }
            }
            else
            {
                if (g.text.offset().top + 4 > g.dateeditor.height() && g.text.offset().top + g.dateeditor.height() + textHeight + 4 - $(window).scrollTop() > $(window).height())
                {
                    g.dateeditor.css("marginTop", -1 * (g.dateeditor.height() + textHeight + 5));
                    g.showOnTop = true;
                }
                else
                {
                    g.showOnTop = false;
                }
            }
        },
        toggleDateEditor: function (isHide)
        {
            var g = this, p = this.options;
            //避免同一界面弹出过个菜单的问题
            var managers = $.ligerui.find($.ligerui.controls.ComboBox);
            for ( var i = 0, l = managers.length; i < l; i++) {
                var o = managers[i];
                if(o.id!=g.id){
                    if(o.selectBox.is(":visible")!=null&&o.selectBox.is(":visible")){
                        o.selectBox.hide();
                    }
                }
            }
            managers = $.ligerui.find($.ligerui.controls.DateEditor);
            for ( var i = 0, l = managers.length; i < l; i++) {
                var o = managers[i];
                if(o.id!=g.id){
                    if(o.dateeditor.is(":visible")!=null&&o.dateeditor.is(":visible")){
                        o.dateeditor.hide();
                    }
                }
            }
            var textHeight = g.text.height();
            g.editorToggling = true;
            if (isHide)
            {
                g.dateeditor.hide('fast', function ()
                {
                    g.editorToggling = false;
                });
            }
            else
            {
                g.updateSelectBoxPosition();
                g.dateeditor.slideDown('fast', function ()
                {
                    g.editorToggling = false;
                });
            }
        },
        showDate: function ()
        {
            var g = this, p = this.options;
            if (!this.currentDate) return;
            this.currentDate.hour = parseInt(g.toolbar.time.hour.html(), 10);
            this.currentDate.minute = parseInt(g.toolbar.time.minute.html(), 10);
            var dateStr = this.currentDate.year + '/' + this.currentDate.month + '/' + this.currentDate.date + ' ' + this.currentDate.hour + ':' + this.currentDate.minute;
            var myDate = new Date(dateStr);
            dateStr = g.getFormatDate(myDate);
            this.inputText.val(dateStr);
            this.onTextChange();
        },
        isDateTime: function (dateStr)
        {
            var g = this, p = this.options;
            var r = dateStr.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
            if (r == null) return false;
            var d = new Date(r[1], r[3] - 1, r[4]);
            if (d == "NaN") return false;
            return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
        },
        isLongDateTime: function (dateStr)
        {
            var g = this, p = this.options;
            var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2})$/;
            var r = dateStr.match(reg);
            if (r == null) return false;
            var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6]);
            if (d == "NaN") return false;
            return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4] && d.getHours() == r[5] && d.getMinutes() == r[6]);
        },
        getFormatDate: function (date)
        {
            var g = this, p = this.options;
            if (date == "NaN") return null;
            var format = p.dateFmt;
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "h+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S": date.getMilliseconds()
            }
            if (/(y+)/.test(format))
            {
                format = format.replace(RegExp.$1, (date.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
            }
            for (var k in o)
            {
                if (new RegExp("(" + k + ")").test(format))
                {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }
            return format;
        },
        clear: function ()
        {
            this.set('value', '');
            this.usedDate = null;
        },
        //取消选择 
        _setCancelable: function (value)
        {
            var g = this, p = this.options;
            if (!value && g.unselect)
            {
                g.unselect.remove();
                g.unselect = null;
            }
            if (!value && !g.unselect) return;
            g.unselect = $('<div class="l-trigger l-trigger-cancel"><div class="l-trigger-icon"></div></div>').hide();
            g.text.hover(function ()
            {
                g.unselect.show();
            }, function ()
            {
                g.unselect.hide();
            })
            if (!p.disabled && p.cancelable)
            {
                g.text.append(g.unselect);
            }
            g.unselect.hover(function ()
            {
                this.className = "l-trigger-hover l-trigger-cancel";
            }, function ()
            {
                this.className = "l-trigger l-trigger-cancel";
            }).click(function ()
            {
                if (p.readonly) return;
                g.clear();
            });
        },
        //恢复
        _rever: function ()
        {
            var g = this, p = this.options;
            if (!g.usedDate)
            {
                g.inputText.val("");
            } else
            {
                g.inputText.val(g.getFormatDate(g.usedDate));
            }
        },
        _getMatch: function (dateFmt)
        {
            var r = [-1, -1, -1, -1, -1, -1], groupIndex = 0, regStr = "^", str = dateFmt || this.options.dateFmt;
            while (true)
            {
                var tmp_r = str.match(/^yyyy|MM|dd|mm|hh|HH|ss|-|\/|:|\s/);
                if (tmp_r)
                {
                    var c = tmp_r[0].charAt(0);
                    var mathLength = tmp_r[0].length;
                    var index = 'yMdhms'.indexOf(c);
                    if (index != -1)
                    {
                        r[index] = groupIndex + 1;
                        regStr += "(\\d{1," + mathLength + "})";
                    } else
                    {
                        var st = c == ' ' ? '\\s' : c;
                        regStr += "(" + st + ")";
                    }
                    groupIndex++;
                    if (mathLength == str.length)
                    {
                        regStr += "$";
                        break;
                    }
                    str = str.substring(mathLength);
                } else
                {
                    return null;
                }
            }
            return { reg: new RegExp(regStr), position: r };
        },
        _bulidDate: function (dateStr)
        {
            var g = this, p = this.options;
            var r = this._getMatch();
            if (!r) return null;
            var t = dateStr.match(r.reg);
            if (!t) return null;
            var tt = {
                y: r.position[0] == -1 ? 1900 : t[r.position[0]],
                M: r.position[1] == -1 ? 0 : parseInt(t[r.position[1]], 10) - 1,
                d: r.position[2] == -1 ? 1 : parseInt(t[r.position[2]], 10),
                h: r.position[3] == -1 ? 0 : parseInt(t[r.position[3]], 10),
                m: r.position[4] == -1 ? 0 : parseInt(t[r.position[4]], 10),
                s: r.position[5] == -1 ? 0 : parseInt(t[r.position[5]], 10)
            };
            if (tt.M < 0 || tt.M > 11 || tt.d < 0 || tt.d > 31) return null;
            var d = new Date(tt.y, tt.M, tt.d);
            if (p.showTime)
            {
                if (tt.m < 0 || tt.m > 59 || tt.h < 0 || tt.h > 23 || tt.s < 0 || tt.s > 59) return null;
                d.setHours(tt.h);
                d.setMinutes(tt.m);
                d.setSeconds(tt.s);
            }
            return d;
        },
        updateStyle: function ()
        {
            this.onTextChange();
        },
        onTextChange: function ()
        {
            var g = this, p = this.options;
            var val = g.inputText.val();
            if (!val)
            {
                g.selectedDate = null;
                return true;
            }
            var newDate = g._bulidDate(val);
            if (!newDate)
            {
                g._rever();
                return;
            }
            else
            {
                g.usedDate = newDate;
            }
            g.selectedDate = {
                year: g.usedDate.getFullYear(),
                month: g.usedDate.getMonth() + 1, //注意这里
                day: g.usedDate.getDay(),
                date: g.usedDate.getDate(),
                hour: g.usedDate.getHours(),
                minute: g.usedDate.getMinutes()
            };
            g.currentDate = {
                year: g.usedDate.getFullYear(),
                month: g.usedDate.getMonth() + 1, //注意这里
                day: g.usedDate.getDay(),
                date: g.usedDate.getDate(),
                hour: g.usedDate.getHours(),
                minute: g.usedDate.getMinutes()
            };
            var formatVal = g.getFormatDate(newDate);
            g.inputText.val(formatVal);
            g.trigger('changeDate', [formatVal]);
            if ($(g.dateeditor).is(":visible"))
                g.bulidContent();
        },
        _setHeight: function (value)
        {
            var g = this;
            if (value > 4)
            {
                g.text.css({ height: value });
                g.inputText.css({ height: value });
                g.textwrapper.css({ height: value });
            }
        },
        _setWidth: function (value)
        {
            var g = this;
            if (value > 20)
            {
                g.text.css({ width: value });
                g.inputText.css({ width: value - 20 });
                g.textwrapper.css({ width: value });
            }
        },
        _setValue: function (value)
        {
            var g = this;
            if (!value) g.inputText.val('');
            if (typeof value == "string")
            {
                if (/^\/Date/.test(value))
                {
                    value = value.replace(/^\//, "new ").replace(/\/$/, "");
                    eval("value = " + value);
                }
                else
                {
                    g.inputText.val(value);
                }
            }
            if (typeof value == "object")
            {
                if (value instanceof Date)
                {
                    g.inputText.val(g.getFormatDate(value));
                    g.onTextChange();
                }
            }
        },
        _getValue: function ()
        {
            return this.usedDate;
        },
        setEnabled: function ()
        {
            var g = this, p = this.options;
            this.inputText.removeAttr("readonly");
            this.text.removeClass('l-text-disabled');
            p.disabled = false;
        },
        setDisabled: function ()
        {
            var g = this, p = this.options;
            this.inputText.attr("readonly", "readonly");
            this.text.addClass('l-text-disabled');
            p.disabled = true;
        }
    });
})(jQuery);