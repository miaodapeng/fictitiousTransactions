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


    var tmpl = TEMPFY("<!-- ## wrap ## -->\r\n<div class=\"dlg-search-select-wrap J-select-dialog-wrap\">\r\n    <div class=\"J-search-block\">\r\n        <div class=\"search-input-wrap\">\r\n            <div class=\"search-input-text\">\r\n                <input type=\"text\" class=\"search-text J-search-input\" placeholder=\"{{=placeholder}}\">\r\n                <i class=\"vd-icon icon-delete J-return\"></i>\r\n            </div>\r\n            <a class=\"search-btn btn btn-small btn-blue-bd J-search-btn\">搜索</a>\r\n        </div>\r\n        <div>\r\n            <table class=\"base-form table table-normal\">\r\n                <colgroup>\r\n                    <col width=\"56px\">\r\n                    {{ $.each(searchList, function(i, obj){ }}\r\n                    <col width=\"{{=obj.width}}\">\r\n                    {{ }) }}\r\n                </colgroup>\r\n                <tbody>\r\n                    <tr>\r\n                        <th></th>\r\n                        {{ $.each(searchList, function(i, obj){ }}\r\n                        <th>{{=obj.label}}</th>\r\n                        {{ }) }}\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n            <div class=\"search-table-list J-search-list-wrap\">\r\n                <table class=\"base-form table table-normal\">\r\n                    <colgroup>\r\n                        <col width=\"56px\">\r\n                        {{ $.each(searchList, function(i, obj){ }}\r\n                        <col width=\"{{=obj.width}}\">\r\n                        {{ }) }}\r\n                    </colgroup>\r\n                    <tbody class=\"J-search-list-cnt\"></tbody>\r\n                </table>\r\n                <div class=\"J-search-pager search-pager-wrap\"></div>\r\n            </div>\r\n            <div class=\"search-select-loading J-search-dialog-loading\"></div>\r\n        </div>\r\n    </div>\r\n    <div class=\"select-search-footer\">\r\n        <button class=\"btn btn-blue btn-disabled J-select-submit\">确定</button>\r\n        <button class=\"btn J-select-cancel\">取消</button>\r\n    </div>\r\n</div>\r\n<!-- ## end ## -->\r\n\r\n<!-- ## searchItem ## -->\r\n{{ if(data.length){ }}\r\n{{ $.each(data, function(i, item){ }}\r\n<tr class=\"J-search-item\">\r\n    <td>\r\n        <div class=\"input-radio\">\r\n            <label class=\"input-wrap\">\r\n                <input type=\"radio\" value=\"{{=item[valueName]}}\" data-obj='{{=JSON.stringify(item)}}' name=\"dialog-search-radio\">\r\n                <span class=\"input-ctnr\"></span>\r\n            </label>\r\n        </div>\r\n    </td>\r\n    {{ $.each(searchList, function(i, obj){ }}\r\n    <td>\r\n        <div class=\"line-clamp1\">\r\n            <span title=\"{{=item[obj.name]}}\">{{=item[obj.name]}}</span>\r\n        </div>\r\n    </td>\r\n    {{ }) }}\r\n</tr>\r\n{{ }) }}\r\n{{ }else{ }}\r\n<tr>\r\n    <td colspan=\"{{=(searchList.length + 1)}}\" class=\"select-list-nodata\">无结果</td>\r\n</tr>\r\n{{ } }}\r\n<!-- ## end ## -->");

    var defaults = {
        el: '',//点击触发的区域
        data: [],
        async: true,//是否异步加载数据
        dataUrl: '',//异步加载接口
        searchList: [{
            label: '表头',
            name: '',
            width: ''
        }],//搜索列表的字段
        placeholder: '',
        title: null,
        searchName: 'keywords',
        valueName: 'value',
        searchInitVal: null,
        input: null
    };

    var DialogSelect = function (config) {
        this.config = $.extend({}, defaults, config);
        this.__init();
        return this;
    }

    DialogSelect.prototype = {
        constructor: 'DialogSelect',
        __init: function () {
            if (!this.config.el) {
                window.console && console.log('先绑定触发元素');
                return false;
            }

            this.$el = $(this.config.el);
            this.__bindEvent();
            this.data = this.config.data;
        },
        __bindEvent: function () {
            var _this = this;
            this.$el.on('click', function () {
                if (typeof artDialog === 'undefined') {
                    window.console && console.log('请先引入artDialog');
                    return false;
                } else {
                    _this.dialog = new artDialog({
                        title: _this.config.title,
                        content: tmpl.wrap({
                            searchList: _this.config.searchList,
                            placeholder: _this.config.placeholder
                        }),
                        width: 640,
                        init: function () {
                            _this.__initDialog();
                        }
                    })
                }
            })

        },
        __refreshList: function (params) {
            var _this = this;
            _this.$loading.show();
            _this.$listWrap.hide();

            var ajaxParams = {
                pageNo: 1,
                pageSize: 10
            };

            ajaxParams = $.extend({}, ajaxParams, params);

            if (this.config.async) {
                setTimeout(function () {
                    $.ajax({
                        url: _this.config.dataUrl,
                        dataType: 'json',
                        data: ajaxParams,
                        async: false,
                        success: function (res) {
                            if (res.code == 0) {
                                var resData = [];

                                if (_this.config.parseData) {
                                    resData = _this.config.parseData(res);
                                } else {
                                    resData = res.data;
                                }

                                _this.$wrap.find('.J-search-list-cnt').empty().append(tmpl.searchItem({
                                    searchList: _this.config.searchList,
                                    data: resData,
                                    valueName: _this.config.valueName
                                }));

                                if (!_this.pager && res.page) {
                                    _this.pager = new Pager({
                                        el: _this.$wrap.find('.J-search-pager'),
                                        total: res.page.totalRocord,
                                        pageSize: 10,
                                        needJump: false,
                                        simple: true,
                                        callback: function (page) {
                                            var params = {
                                                pageNo: page,
                                            };

                                            params[_this.config.searchName] = _this.searchVal;

                                            _this.__refreshList(params);
                                        }
                                    })
                                }

                                _this.__checkSubmit();
                                _this.__checkHover();
                                _this.$listWrap.show();
                                _this.$loading.hide();
                                _this.dialog.reset();
                            }
                        }
                    })
                }, 500)
            }
        },
        __search: function (page) {
            var val = $.trim(this.$searchInput.val());
            var params = {
                pageNo: page || 1
            };

            if (val) {
                params[this.config.searchName] = val;
                this.__refreshList(params);
                this.__destroyPager();
                this.searchVal = val;
                this.$return.show();
            } else {
                this.$searchInput.focus();
            }
        },
        __checkSubmit: function () {
            var $check = $('.J-search-item input:checked');

            if ($check.length) {
                $('.J-select-submit', this.$wrap).removeClass('btn-disabled');
            } else {
                $('.J-select-submit', this.$wrap).addClass('btn-disabled');
            }
        },
        __checkHover: function () {
            var $table = $('.J-search-list-cnt', this.$wrap).parents('table:first');
            if ($('.J-search-item', this.$wrap).length) {
                $table.addClass('table-hover');
            } else {
                $table.removeClass('table-hover');
            }
        },
        __initDialog: function () {
            var _this = this;
            this.$wrap = $('.J-select-dialog-wrap');
            this.$listWrap = $('.J-search-list-wrap', this.$wrap);
            this.$loading = $('.J-search-dialog-loading', this.$wrap);
            this.$searchInput = $('.J-search-input', this.$wrap);
            this.$return = $('.J-return', this.$wrap);
            this.pager = null;
            var params = {};

            if (this.config.searchInitVal) {
                var searchVal = this.config.searchInitVal();
                if (searchVal) {
                    params[this.config.searchName] = this.config.searchInitVal();
                    this.$searchInput.val(this.config.searchInitVal());
                    this.$return.show();
                }
            }

            this.__refreshList(params);

            this.$wrap.on('click', '.J-search-item', function () {
                $(this).find('input:radio')[0].checked = true;
                _this.__checkSubmit();
            });

            $('.J-search-btn', this.$wrap).on('click', function () {
                _this.__search();
            });

            this.$searchInput.on('keyup', function (e) {
                if (e.keyCode == 13 || e.keyCode == 108) {
                    _this.__search();
                }
            });

            this.$return.click(function () {
                _this.$searchInput.val('');
                _this.searchVal = '';
                $(this).hide();
                _this.__destroyPager();
                _this.__refreshList({
                    pageNo: 1
                });
            });

            $('.J-select-submit', this.$wrap).click(function () {
                if (!$(this).hasClass('btn-disabled')) {
                    var $checked = $('.J-search-item input:checked');
                    var val = $checked.val();
                    var obj = $checked.data('obj');
                    _this.config.input && $(_this.config.input).val(val);

                    _this.config.onselect && _this.config.onselect(val, obj);
                    _this.dialog.close();
                }
            });

            $('.J-select-cancel', this.$wrap).click(function () {
                _this.dialog.close();
            });
        },
        __destroyPager: function () {
            this.pager = null;
            var $pager = this.$wrap.find('.J-search-pager');
            $pager.after('<div class="J-search-pager search-pager-wrap"></div>');
            $pager.remove();
        }
    }

    window.DialogSelect = DialogSelect;

}.call(this);