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
var TEMP = { tpl: function (t, e) { e = e || /\<\!--\s*#+\s*([\w\.]+)\s*#+\s*--\>([\w\W]*?)\<\!--\s*#+\s*end\s*#+\s*--\>/gi, t = t.toString() || "", e.exec(""); for (var r = {}, n = null; n = e.exec(t);)for (var i = n[1].split("."), l = r; i.length;) { var a = i.shift(); a in l || (l[a] = {}), i.length || (l[a] = n[2].replace(/^[\s\u00A0]+|[\s\u00A0]+$/g, "")), l = l[a] } return r } }; TEMP.templatify = function () { var n = function () { return "" }, i = Object.prototype.toString; return function (t) { var e = n; if ("string" == typeof t) e = template(t); else if ("[object Object]" === i.call(t)) for (var r in e = {}, t) e[r] = TEMP.templatify(t[r]); return e } }.call(this); var TEMPFY = function (t) { return TEMP.templatify(TEMP.tpl(t)) };//@import(common/lib/template.js)

; void function () {


    var tmpl = TEMPFY("<!-- ## wrap ## -->\r\n<div class=\"select-lv\">\r\n    <div class=\"select-title J-select-title\">\r\n        <span class=\"select-label J-label\" style=\"display:none\"></span>\r\n        <div class=\"select-selected\">\r\n            <span class=\"J-text\">请选择</span>\r\n        </div>\r\n        <div class=\"select-arrow J-arrow\">\r\n            <i class=\"vd-icon icon-down\"></i>\r\n        </div>\r\n    </div>\r\n    <div class=\"select-list J-select-list\">\r\n        <ul class=\"select-list-wrap J-select-tab-cnt\">\r\n            {{ $.each(data, function(i, item){ }}\r\n            <li>\r\n                <a href=\"javascript:void('{{=item.value}}')\" data-id=\"{{=item.value}}\"\r\n                    class=\"select-opt J-select-lv1\">{{=item.label}}</a>\r\n            </li>\r\n            {{ }); }}\r\n        </ul>\r\n    </div>\r\n</div>\r\n<!-- ## end ## -->\r\n\r\n<!-- ## itemlv2 ## -->\r\n<ul class=\"select-list-wrap J-lv2-wrap\">\r\n    {{ $.each(data, function(i, item){ }}\r\n    <li>\r\n        <a href=\"javascript:void('{{=item.value}}')\" data-id=\"{{=item.value}}\" class=\"select-opt J-select-lv2\">{{=item.label}}</a>\r\n    </li>\r\n    {{ }); }}\r\n</ul>\r\n<!-- ## end ## -->\r\n\r\n<!-- ## itemlv3 ## -->\r\n<ul class=\"select-list-wrap J-lv3-wrap\">\r\n    {{ $.each(data, function(i, item){ }}\r\n    <li>\r\n        <a href=\"javascript:void('{{=item.value}}')\" data-id=\"{{=item.value}}\" class=\"select-opt J-select-lv3\">{{=item.label}}</a>\r\n    </li>\r\n    {{ }); }}\r\n</ul>\r\n<!-- ## end ## -->");

    var defaults = {
        el: '.J-lvSelect',
        value: null,
        onchange: function () { },
        async: false,
        url: '',
        data: [],
        input: '.J-lv-select'
    };

    var LvSelect = function (config) {
        this.config = $.extend({}, defaults, config);
        this.__init();
        return this;
    }

    LvSelect.prototype = {
        constructor: 'LvSelect',
        __init: function () {
            this.$wrapper = $(this.config.el);
            this.$input = $(this.config.input);
            var _this = this;

            this.lv1Data = {};
            this.lv2Data = {};
            this.lv3Data = {};

            if (this.config.async) {
                $.ajax({
                    url: this.config.url,
                    dataType: 'json',
                    success: function (res) {
                        if (res.code == 0) {
                            if (_this.config.parseData) {
                                _this.data = _this.config.parseData(res);
                            } else {
                                _this.data = res.data;
                            }
                            _this.__initTmpl();
                            _this.__bindEvent();
                        }
                    }
                })
            } else {
                this.data = this.config.data;
                this.__initTmpl();
                this.__bindEvent();
            }
        },
        __initTmpl: function () {
            this.$wrapper.append(tmpl.wrap({ data: this.data }));
            this.$drop = this.$wrapper.find('.J-select-title');
            this.$text = this.$wrapper.find('.J-text');
            if (this.$input.val()) {
                this.__initValue();
            }
        },
        __bindEvent: function () {
            var _this = this;

            this.$wrapper.on('click', '.J-select-title', function () {
                _this.__showList();
            })

            this.$wrapper.on('click', '.J-select-lv1', function () {
                _this.$wrapper.find('.J-lv2-wrap, .J-lv3-wrap').remove();
                _this.lv2Data = {};
                _this.lv3Data = {};
                _this.__getData($(this).data('id'), 1);
            })

            this.$wrapper.on('click', '.J-select-lv2', function () {
                _this.$wrapper.find('.J-lv3-wrap').remove();
                _this.lv3Data = {};
                _this.__getData($(this).data('id'), 2);
            })

            this.$wrapper.on('click', '.J-select-lv3', function () {
                _this.__getData($(this).data('id'), 3);
                _this.__setValue();
            })

            $(document).click(function () {
                _this.__hideList();
            })

            this.$wrapper.click(function (e) {
                e.stopPropagation();
            })
        },
        __showList: function () {
            this.$wrapper.find('.J-select-list').addClass('show');
            this.__scroll();
        },
        __hideList: function () {
            this.$wrapper.find('.J-select-list').removeClass('show');
        },
        __getData: function (id, lv) {
            var _this = this;
            var data = [this.data, this.lv1Data.child, this.lv2Data.child][lv - 1];

            $.each(data, function (i, obj) {
                if (id == obj.value) {
                    _this['lv' + lv + 'Data'] = obj;
                }
            })

            var childData = _this['lv' + lv + 'Data'].child;

            if (lv < 3) {
                if (childData && childData.length) {
                    $('.J-select-list', _this.$wrapper).append(tmpl['itemlv' + (lv + 1)]({ data: childData }));
                } else {
                    _this.__setValue();
                }
            }

            this.__resetDom(id, lv);
        },
        __resetDom: function (id, lv) {
            var $selector = $('.J-select-lv' + lv, this.$wrapper);

            $selector.each(function () {
                $(this).removeClass('selected');
                if ($(this).data('id') == id) {
                    $(this).addClass('selected');
                }
            });
        },
        __initValue: function () {
            var _this = this;
            var value = this.$input.val();
            var initVal = [null, null, null];
            $.each(this.data, function (i, lv1) {
                if (lv1.value == value) {
                    initVal[0] = lv1.value;
                }
                $.each(lv1.child, function (ii, lv2) {
                    if (lv2.value == value) {
                        initVal[0] = lv1.value;
                        initVal[1] = lv2.value;
                    }
                    $.each(lv2.child, function (iii, lv3) {
                        if (lv3.value == value) {
                            initVal[0] = lv1.value;
                            initVal[1] = lv2.value;
                            initVal[2] = lv3.value;
                        }
                    })
                })
            })

            $.each(initVal, function (i, item) {
                if (item) {
                    _this.__getData(item, i + 1);
                }
            })

            this.__setValue();
        },
        __scroll: function () {
            this.$wrapper.find('.select-list-wrap').each(function () {
                var $selected = $(this).find('.selected');

                if ($selected.length) {
                    $(this).scrollTop($selected[0].offsetTop);
                }
            })
        },
        __setValue: function () {
            var label = this.lv1Data.label + (this.lv2Data.label ? '>' + this.lv2Data.label : '') + (this.lv3Data.label ? '>' + this.lv3Data.label : '');
            var value = this.lv3Data.value || this.lv2Data.value || this.lv1Data.value;

            this.$text.html(label);
            this.$input.val(value);
            this.config.onchange(value);
            this.__hideList();
        }
    }

    window.LvSelect = LvSelect;


}.call(this);