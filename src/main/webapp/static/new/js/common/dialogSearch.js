var template = function () {
    var c = {}, x = Object.prototype.hasOwnProperty, r = Array.prototype.indexOf, s = Array.prototype.some, t = Array.prototype.filter, u = Array.prototype.map, n = {}; c.type = function (a) { var b, d = /\{\s*\[native\s*code\]\s*\}/i; null === a ? b = "null" : "undefined" === typeof a ? b = "undefined" : (b = Object.prototype.toString.call(a).match(/\w+/g)[1].toLowerCase(), "object" === b && d.test(a + "") && (b = "function")); return b }; c.trim = function (a) { return (a + "").replace(/^[\s\u00A0]+|[\s\u00A0]+$/g, "") }; c.extend = function () {
        var a =
            arguments.callee, b, d; "object" !== c.type(arguments[0]) ? (b = 1, d = !!arguments[0]) : (b = 0, d = !1); var e = arguments[b] || {}; b = [].slice.call(arguments, b + 1); for (var k, h; b.length;)if (k = b.shift(), "object" === c.type(k)) {
                var f, g; for (g in k) if (f = k[g], "object" === c.type(f)) if (f == window || f == document || "childNodes" in f && "nextSibling" in f && "nodeType" in f) { if (d || !(g in e)) e[g] = f } else if (f.jquery && /^[\d\.]+$/.test(f.jquery)) e[g] = f; else {
                    h = c.type(e[g]); if (!(g in e) || "undefined" === h || "null" === h || d && ("string" === h || "number" === h || "bool" ===
                        h)) e[g] = {}; "object" === c.type(e[g]) && a(d, e[g], f)
                } else if (d || !(g in e)) e[g] = f
            } return e
    }; var p = c.each = function (a, b, d) { if (null != a) if ([].forEach && a.forEach === [].forEach) a.forEach(b, d); else if (a.length === +a.length) for (var e = 0, k = a.length; e < k && b.call(d, a[e], e, a) !== n; e++); else for (e in a) if (c.has(a, e) && b.call(d, a[e], e, a) === n) break }; c.has = function (a, b) { return x.call(a, b) }; c.identity = function (a) { return a }; var v = c.some = c.any = function (a, b, d) {
        b || (b = c.identity); var e = !1; if (null == a) return e; if (s && a.some === s) return a.some(b,
            d); p(a, function (a, c, f) { if (e || (e = b.call(d, a, c, f))) return n }); return !!e
    }; c.find = c.detect = function (a, b, d) { var e; v(a, function (a, c, f) { if (b.call(d, a, c, f)) return e = a, !0 }); return e }; c.contains = c.include = function (a, b) { return null == a ? !1 : r && a.indexOf === r ? -1 != a.indexOf(b) : v(a, function (a) { return a === b }) }; c.filter = c.select = function (a, b, d) { var c = []; if (null == a) return c; if (t && a.filter === t) return a.filter(b, d); p(a, function (a, h, f) { b.call(d, a, h, f) && (c[c.length] = a) }); return c }; c.map = c.collect = function (a, b, c) {
        var e = [];
        if (null == a) return e; if (u && a.map === u) return a.map(b, c); p(a, function (a, h, f) { e[e.length] = b.call(c, a, h, f) }); return e
    }; c.invert = function (a) { var b = {}, d; for (d in a) c.has(a, d) && (b[a[d]] = d); return b }; c.keys = Object.keys || function (a) { if (a !== Object(a)) throw new TypeError("Invalid object"); var b = [], d; for (d in a) c.has(a, d) && (b[b.length] = d); return b }; c.values = function (a) { var b = [], d; for (d in a) c.has(a, d) && b.push(a[d]); return b }; c.random = function (a, b) { null == b && (b = a, a = 0); return a + Math.floor(Math.random() * (b - a + 1)) };
    var l = { escape: { "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#x27;", "/": "&#x2F;" } }; l.unescape = c.invert(l.escape); var y = { escape: RegExp("[" + c.keys(l.escape).join("") + "]", "g"), unescape: RegExp("(" + c.keys(l.unescape).join("|") + ")", "g") }; c.each(["escape", "unescape"], function (a) { c[a] = function (b) { return null == b ? "" : ("" + b).replace(y[a], function (b) { return l[a][b] }) } }); var w = { evaluate: /{{([\s\S]+?)}}/g, interpolate: /{{=([\s\S]+?)}}/g, escape: /{{-([\s\S]+?)}}/g }, q = /(.)^/, z = {
        "'": "'", "\\": "\\", "\r": "r", "\n": "n",
        "\t": "t", "\u2028": "u2028", "\u2029": "u2029"
    }, A = /\\|'|\r|\n|\t|\u2028|\u2029/g, m = function (a, b, d) {
        var e; d = c.extend(!0, {}, w, d); var k = RegExp([(d.escape || q).source, (d.interpolate || q).source, (d.evaluate || q).source].join("|") + "|$", "g"), h = 0, f = "__p+='"; a.replace(k, function (b, c, d, e, g) { f += a.slice(h, g).replace(A, function (a) { return "\\" + z[a] }); c && (f += "'+\n((__t=(" + c + "))==null?'':util.escape(__t))+\n'"); d && (f += "'+\n((__t=(" + d + "))==null?'':__t)+\n'"); e && (f += "';\n" + e + "\n__p+='"); h = g + b.length; return b }); f += "';\n"; d.variable ||
            (f = "with(obj||{}){\n" + f + "}\n"); f = "var __t,__p='',__j=Array.prototype.join,print=function(){__p+=__j.call(arguments,'');};\n" + f + "return __p;\n"; try { e = new Function(d.variable || "obj", "util", f) } catch (g) { throw g.source = f, g; } if (b) return e(b, c); b = function (a) { return e.call(this, a, c) }; b.source = "function(" + (d.variable || "obj") + "){\n" + f + "}"; return b
    }; m.util = c; m.entities = l; m.settings = w; return m
}.call(this);
var TEMP = { tpl: function (t, e) { e = e || /\<\!--\s*#+\s*([\w\.]+)\s*#+\s*--\>([\w\W]*?)\<\!--\s*#+\s*end\s*#+\s*--\>/gi, t = t.toString() || "", e.exec(""); for (var r = {}, n = null; n = e.exec(t);)for (var i = n[1].split("."), l = r; i.length;) { var a = i.shift(); a in l || (l[a] = {}), i.length || (l[a] = n[2].replace(/^[\s\u00A0]+|[\s\u00A0]+$/g, "")), l = l[a] } return r } }; TEMP.templatify = function () { var n = function () { return "" }, i = Object.prototype.toString; return function (t) { var e = n; if ("string" == typeof t) e = template(t); else if ("[object Object]" === i.call(t)) for (var r in e = {}, t) e[r] = TEMP.templatify(t[r]); return e } }.call(this); var TEMPFY = function (t) { return TEMP.templatify(TEMP.tpl(t)) };/**
 * 依赖 artdialog.js
 */

//@import(common/lib/template.js)

; void function () {


    var tmpl = TEMPFY("<!-- ## wrap ## -->\r\n<div class=\"dlg-search-wrap J-search-dialog-wrap\">\r\n    <div class=\"search-select-loading J-search-dialog-loading\"></div>\r\n</div>\r\n<!-- ## end ## -->\r\n\r\n<!-- ## content ## -->\r\n{{ if(needTab && needTab.length){ }}\r\n<div class=\"search-list-tab\">\r\n    {{ $.each(needTab, function(i, obj){ }}\r\n    <div class=\"search-tab-item J-search-tab {{if(i==0){}}current{{}}}\">{{=obj}}</div>\r\n    {{ }) }}\r\n</div>\r\n{{ } }}\r\n<div class=\"J-search-block\">\r\n    <div class=\"search-input-wrap\">\r\n        <div class=\"search-input-text\">\r\n            <input type=\"text\" class=\"search-text J-search-input\" placeholder=\"{{=placeholder}}\">\r\n            <i class=\"vd-icon icon-delete J-return\"></i>\r\n        </div>\r\n        <a class=\"search-btn btn btn-small btn-blue-bd J-search-btn\">搜索</a>\r\n    </div>\r\n    <div class=\"search-select-list J-select-lv-list\">\r\n        {{ if(data.length){ }}\r\n        <div class=\"select-list-wrap\">\r\n            {{ $.each(data, function(i, item){ }}\r\n            <div class=\"select-opt J-select-lv1\" data-value=\"{{=item.value}}\" data-label=\"{{=item.label}}\">{{=item.label}}</div>\r\n            {{ }) }}\r\n        </div>\r\n        {{ }else{ }}\r\n        <div class=\"select-list-nodata\">无结果</div>\r\n        {{ } }}\r\n    </div>\r\n    <div class=\"J-select-search-list\" style=\"display: none;\">\r\n        <div class=\"J-search-list-cnt\"></div>\r\n    </div>\r\n</div>\r\n{{ if(needTab && needTab.length){ }}\r\n<div class=\"J-search-block search-history-block\"></div>\r\n{{ } }}\r\n<div class=\"select-search-footer\">\r\n    <button class=\"btn btn-blue J-select-submit\">确定</button>\r\n    <button class=\"btn J-select-cancel\">取消</button>\r\n</div>\r\n<!-- ## end ## -->\r\n\r\n<!-- ## lvItem ## -->\r\n<div class=\"select-list-wrap J-lv{{=lv}}-wrap\">\r\n    {{ $.each(data, function(i, item){ }}\r\n    <div class=\"select-opt J-select-lv{{=lv}}\" data-params='{{=JSON.stringify(item)}}' data-value=\"{{=item.value}}\" data-label=\"{{=item.label}}\">{{=item.label}}</div>\r\n    {{ }); }}\r\n</div>\r\n<!-- ## end ## -->\r\n\r\n<!-- ## searchItem ## -->\r\n<table class=\"base-form table table-normal\">\r\n    <colgroup>\r\n        <col width=\"56px\">\r\n        {{ $.each(searchList, function(i, obj){ }}\r\n        <col width=\"{{=obj.width}}\">\r\n        {{ }) }}\r\n    </colgroup>\r\n    <tbody>\r\n        <tr>\r\n            <th></th>\r\n            {{ $.each(searchList, function(i, obj){ }}\r\n            <th>{{=obj.label}}</th>\r\n            {{ }) }}\r\n        </tr>\r\n    </tbody>\r\n</table>\r\n<div class=\"search-table-list\">\r\n    <table class=\"base-form table table-normal {{ if(data.length){ }}table-hover{{ } }}\">\r\n        <colgroup>\r\n            <col width=\"56px\">\r\n            {{ $.each(searchList, function(i, obj){ }}\r\n            <col width=\"{{=obj.width}}\">\r\n            {{ }) }}\r\n        </colgroup>\r\n        <tbody>\r\n            {{ if(data.length){ }}\r\n            {{ $.each(data, function(i, item){ }}\r\n            <tr class=\"J-search-item\">\r\n                <td>\r\n                    <div class=\"input-radio\">\r\n                        <label class=\"input-wrap\">\r\n                            <input type=\"radio\" data-params='{{=JSON.stringify(item)}}' data-value=\"{{=item.value}}\" data-label=\"{{=item[searchList[0].name]}}\" name=\"dialog-search\">\r\n                            <span class=\"input-ctnr\"></span>\r\n                        </label>\r\n                    </div>\r\n                </td>\r\n                {{ $.each(searchList, function(i, obj){ }}\r\n                <td>{{=item[obj.name]}}</td>\r\n                {{ }) }}\r\n            </tr>\r\n            {{ }) }}\r\n            {{ }else{ }}\r\n            <tr>\r\n                <td colspan=\"{{=(searchList.length + 1)}}\" class=\"select-list-nodata\">无结果</td>\r\n            </tr>\r\n            {{ } }}\r\n        </tbody>\r\n    </table>\r\n</div>\r\n<!-- ## end ## -->");

    var defaults = {
        el: '.J-search-dialog-trigger',//点击触发的区域
        data: [],//初始化的三级联动的数据
        searchUrl: '',//搜索的接口url
        input: '',//选择的值
        label: '',//选择的label
        placeholder: '',//搜索框的placeholder
        onselect: null,//选择之后回调,
        params: 'keywords',//搜索的参数名
        dataUrl: '',
        async: false,
        needTab: null,
        title: '请选择',
        searchList: [{
            label: '',
            name: '',
            width: ''
        }], //搜索出来的列表页面的字段名，取值以及宽度
        historyName: null, //需要存入storage记录历史的字段名，建议取值 "当前页面名"+"字段名" 避免重复
        dataparse: null,
        parseSearchData: null
    };

    var DialogSearch = function (config) {
        this.config = $.extend({}, defaults, config);
        this.__init();
        return this;
    }

    DialogSearch.prototype = {
        constructor: 'DialogSearch',
        __init: function () {
            this.$el = $(this.config.el);
            this.$wrap = null;
            this.$input = $(this.config.input);
            this.$label = $(this.config.label);
            this.dialog = null;
            this.__bindEvent();
            this.lv2Data = [];
            this.lv3Data = [];
            this.lv1Vlaue = null;
            this.lv2Vlaue = null;
            this.lv3Vlaue = null;
            this.isSearch = false;
            this.canSubmit = false;

            var _this = this;

            this.__resetData({
                callback: function () {
                    _this.__setInitData();
                }
            })

        },
        __resetData: function (obj) {
            var _this = this;
            if (_this.config.async) {
                $.ajax({
                    url: this.config.dataUrl,
                    dataType: 'json',
                    success: function (res) {
                        if (res.code == 0) {
                            if (_this.config.dataparse) {
                                _this.config.data = _this.config.dataparse(res);
                            } else {
                                _this.config.data = res.data;
                            }

                            obj && obj.callback && obj.callback();
                        }
                    }
                })
            } else {
                obj && obj.callback && obj.callback();
            }
        },
        __bindEvent: function () {
            var _this = this;
            this.$el.on('click', function () {
                if (typeof artDialog === 'undefined') {
                    window.console && console.log('请先引入artDialog');
                } else {
                    _this.isSearch = false;
                    _this.lv1Vlaue = null;
                    _this.lv2Vlaue = null;
                    _this.lv3Vlaue = null;
                    _this.dialog = new artDialog({
                        title: _this.config.title,
                        content: tmpl.wrap(),
                        width: 640,
                        init: function () {
                            _this.__resetData({
                                callback: function () {
                                    $('.J-search-dialog-wrap').append(tmpl.content({ data: _this.config.data, placeholder: _this.config.placeholder, needTab: _this.config.needTab }));
                                    $('.J-search-dialog-loading').hide();
                                    _this.__initDialog();
                                }
                            })
                        }
                    })
                }
            })

        },
        __initDialog: function () {
            var $wrap = this.$wrap = $('.J-search-dialog-wrap');
            var _this = this;

            $wrap.on('click', '.J-select-lv1', function () {
                var value = $(this).data('value');
                $('.J-select-lv1', $wrap).removeClass('selected');
                $(this).addClass('selected');

                if (_this.lv1Vlaue != value) {
                    $wrap.find('.J-lv2-wrap, .J-lv3-wrap').remove();
                    _this.lv1Vlaue = value;
                    _this.__setValue(value, 2);
                }

                _this.__checkSubmit();
            });

            $wrap.on('click', '.J-select-lv2', function () {
                var value = $(this).data('value');
                $('.J-select-lv2', $wrap).removeClass('selected');
                $(this).addClass('selected');

                if (_this.lv2Vlaue != value) {
                    $wrap.find('.J-lv3-wrap').remove();
                    _this.lv2Vlaue = value;
                    _this.__setValue(value, 3);
                }

                _this.__checkSubmit();
            });

            $wrap.on('click', '.J-select-lv3', function () {
                var value = $(this).data('value');
                $('.J-select-lv3', $wrap).removeClass('selected');
                $(this).addClass('selected');
                this.lv3Vlaue = value;

                _this.__checkSubmit();
            });

            $wrap.on('click', '.J-search-btn', function () {
                var val = $.trim($('.J-search-input', $wrap).val());
                if (val) {
                    _this.__search(val);
                } else if (!val) {
                    $('.J-search-input', $wrap).focus();
                }
            });

            $wrap.on('click', '.J-return', function () {
                $('.J-select-lv-list', $wrap).show();
                $(this).hide();
                $('.J-select-search-list', $wrap).hide();
                $('.J-search-input', $wrap).val('');
                _this.isSearch = false;
                _this.__checkSubmit();
            });

            $wrap.on('keyup', '.J-search-input', function (e) {
                var val = $.trim($(this).val());
                if ((e.keyCode == 13 || e.keyCode == 108) && val) {
                    _this.__search(val);
                }
            });

            $wrap.on('click', '.J-search-item', function () {
                $(this).find('input:radio')[0].checked = true;

                _this.__checkSubmit();
            });

            $wrap.on('click', '.J-select-submit', function () {
                _this.__submit();
            });

            $wrap.on('click', '.J-select-cancel', function () {
                _this.dialog.close();
            });

            $wrap.on('click', '.J-search-tab', function () {
                if (!$(this).hasClass('current')) {
                    $('.J-search-tab').removeClass('current');
                    $(this).addClass('current');

                    $('.J-search-block').hide();
                    $('.J-search-block').eq($(this).index()).show();

                    if ($(this).index() != 0) {
                        $('.J-search-block').eq($(this).index()).empty().append(tmpl.searchItem({
                            data: _this.__getHistory(),
                            searchList: _this.config.searchList
                        }))
                    }

                    _this.__checkSubmit();
                }
            })

            this.__checkInitVal();
            this.__checkSubmit();
        },
        __setValue: function (value, lv) {
            var datas = {
                1: this.config.data,
                2: this.lv2Data,
                3: this.lv3Data
            }
            var _this = this;

            _this['lv' + lv + 'Data'] = [];

            $.each(datas[lv - 1], function (i, item) {
                if (item.value == value && item.child) {
                    _this['lv' + lv + 'Data'] = item.child;
                }
            })

            if (this['lv' + lv + 'Data'] && this['lv' + lv + 'Data'].length) {
                $('.J-select-lv-list', _this.$wrap).append(tmpl.lvItem({
                    data: this['lv' + lv + 'Data'],
                    lv: lv
                }))
            }
        },
        __checkInitVal: function () {
            var initVal = $.trim(this.$input.val());

            if (initVal) {
                $.each(this.config.data, function (i1, obj1) {
                    if (obj1.value == initVal) {
                        $('.J-select-lv1[data-value = "' + obj1.value + '"]').trigger('click');
                    }
                    $.each(obj1.child, function (i2, obj2) {
                        if (obj2.value == initVal) {
                            $('.J-select-lv1[data-value = "' + obj1.value + '"]').trigger('click');
                            $('.J-select-lv2[data-value = "' + obj2.value + '"]').trigger('click');
                        }
                        $.each(obj2.child, function (i3, obj3) {
                            if (obj3.value == initVal) {
                                $('.J-select-lv1[data-value = "' + obj1.value + '"]').trigger('click');
                                $('.J-select-lv2[data-value = "' + obj2.value + '"]').trigger('click');
                                $('.J-select-lv3[data-value = "' + obj3.value + '"]').trigger('click');
                            }
                        })
                    })
                });
            }

            this.__scroll();
        },
        __scroll: function () {
            this.$wrap.find('.select-list-wrap').each(function () {
                var $selected = $(this).find('.selected');

                if ($selected.length) {
                    $(this).scrollTop($selected[0].offsetTop - 60);
                }
            })
        },
        __search: function (val) {
            $('.J-select-lv-list', this.$wrap).hide();
            $('.J-select-search-list, .J-return', this.$wrap).show();
            $('.J-search-list-cnt', this.$wrap).empty();
            this.isSearch = true;
            var _this = this;
            var data = {};
            this.config.params && (data[this.config.params] = val);

            $.ajax({
                url: this.config.searchUrl,
                data: data,
                dataType: 'json',
                success: function (res) {
                    if (res.code == 0) {
                        var resdata = []

                        if (_this.config.parseSearchData) {
                            resdata = _this.config.parseSearchData(res);
                        } else {
                            resdata = res.data;
                        }

                        $('.J-search-list-cnt', _this.$wrap).append(tmpl.searchItem({
                            data: resdata,
                            searchList: _this.config.searchList
                        }))
                        _this.__checkSubmit();
                    }
                },
                error: function () { }
            })
            // $('.J-search-list-cnt', this.$wrap).append(tmpl.searchItem({
            //     data: ajaxdata || []
            // }))

            // _this.__checkSubmit();
        },
        __checkSubmit: function () {
            var len = 0;
            var index = $('.J-search-tab.current', this.$wrap).index();

            if ($('.J-search-tab', this.$wrap).length && index > 0) {
                len = $('.J-search-block', this.$wrap).eq(index).find('.J-search-item input:radio:checked').length;
            } else {
                if (this.isSearch) {
                    len = $('.J-search-block', this.$wrap).eq(0).find('.J-search-item input:radio:checked').length;
                } else {
                    var $lastLv = $('.J-select-lv-list', this.$wrap).find('.select-list-wrap:last');
                    len = $lastLv.find('.select-opt.selected').length;
                }
            }

            if (len) {
                this.canSubmit = true;
                $('.J-select-submit', this.$wrap).removeClass('btn-disabled');
            } else {
                this.canSubmit = false;
                $('.J-select-submit', this.$wrap).addClass('btn-disabled');
            }
        },
        __submit: function () {
            if (this.canSubmit) {
                var obj = {
                    value: '',
                    label: ''
                };

                var params = {};

                if (this.isSearch || $('.J-search-tab.current', this.$wrap).index() > 0) {
                    var $select = $('.J-search-item input:radio:checked', this.$wrap);
                    obj.value = $select.data('value');
                    obj.label = $select.data('label');
                    params = $select.data('params');
                } else {
                    var $selectWrap = $('.J-select-lv-list', this.$wrap).find('.select-list-wrap');
                    var $select = $('.select-opt.selected', $selectWrap);

                    $select.each(function (i, item) {
                        obj.value = $(this).data('value');
                        obj.label += (i === 0 ? '' : ' > ') + $(this).data('label');
                        params = $(this).data('params');
                    })
                }

                this.config.onselect && this.config.onselect(obj, params);
                this.$input.val(obj.value);
                this.$label.html(obj.label);
                this.dialog.close();
            }
        },
        __getHistory: function () {
            var history = window.localStorage.getItem(this.config.historyName);

            historyList = history ? JSON.parse(history) : [];

            return historyList;
        },
        __setInitData: function () {
            var initVal = $.trim(this.$input.val());
            var initeArr = [];

            if (initVal) {
                $.each(this.config.data, function (i1, obj1) {
                    if (obj1.value == initVal) {
                        initeArr.push(obj1.label);
                    }
                    $.each(obj1.child, function (i2, obj2) {
                        if (obj2.value == initVal) {
                            initeArr.push(obj1.label);
                            initeArr.push(obj2.label);
                        }
                        $.each(obj2.child, function (i3, obj3) {
                            if (obj3.value == initVal) {
                                initeArr.push(obj1.label);
                                initeArr.push(obj2.label);
                                initeArr.push(obj3.label);
                            }
                        })
                    })
                });

                this.$label.html(initeArr.join('>') || '请选择');
            }
        }
    }

    window.DialogSearch = DialogSearch;

}.call(this);