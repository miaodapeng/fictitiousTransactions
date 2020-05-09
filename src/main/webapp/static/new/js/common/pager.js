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


    var tmpl = TEMPFY("<!-- ## wrap ## -->\r\n{{ if(max > 1){ }}\r\n<div class=\"pager-wrap J-async-pager-wrap\">\r\n    <div class=\"page-num\">\r\n        <a href=\"javascript:void(0)\" class=\"page-prev J-pager-item {{ if(pageNum == 1){ }}page-disabled{{ } }}\" data-current=\"{{=pageNum-1}}\" rel=\"nofollow\"><i class=\"vd-icon icon-left\"></i>上一页</a>\r\n        <a href=\"javascript:void(0)\" class=\"{{ if(pageNum == 1){ }}page-current{{ } }} J-pager-item\" data-current=\"1\" rel=\"nofollow\">1</a>\r\n\r\n        {{ if(max > 9 && pageNum > 5){ }}\r\n        <strong class=\"omit\">...</strong>\r\n        {{ } }}\r\n\r\n        {{ for(var i = range[0]; i <= range[1]; i++){ }}\r\n        <a href=\"javascript:void(0)\" class=\"J-pager-item {{ if(pageNum == i){ }}page-current{{ } }}\" data-current=\"{{=i}}\" rel=\"nofollow\">{{=i}}</a>\r\n        {{ } }}\r\n\r\n        {{ if(max > 9 && pageNum < max - 4){ }}\r\n        <strong class=\"omit\">...</strong>\r\n        {{ } }}\r\n        <a href=\"javascript:void(0)\" class=\"{{ if(pageNum == max){ }}page-current{{ } }} J-pager-item\" data-current=\"{{=max}}\" rel=\"nofollow\">{{=max}}</a>\r\n        <a href=\"javascript:void(0)\" class=\"page-next J-pager-item {{ if(pageNum == max){ }}page-disabled{{ } }}\" data-current=\"{{=pageNum+1}}\" rel=\"nofollow\">下一页<i class=\"vd-icon icon-right\"></i></a>\r\n        {{ if(needJump){ }}\r\n        <div class=\"page-jump\">\r\n            <span class=\"page-jump-txt\">到第：</span>\r\n            <input type=\"text\" class=\"page-input J-pager-input\">\r\n            <a href=\"javascript:void(0)\" class=\"page-jump-btn J-pager-jump\" data-link=\"javascript:void(0)\">确定</a>\r\n        </div>\r\n        {{}}}\r\n    </div>\r\n</div>\r\n{{ } }}\r\n<!-- ## end ## -->");

    var defaults = {
        el: null,
        pageNum: 1,
        total: 91,
        pageSize: 10,
        needJump: true,
        callback: null
    };

    var Pager = function (config) {
        this.config = $.extend({}, defaults, config);
        this.__init();
        return this;
    }

    Pager.prototype = {
        constructor: 'Pager',
        __init: function () {
            this.$wrap = $(this.config.el);

            var config = this.config;
            this.max = Math.ceil(config.total / config.pageSize);
            var params = $.extend({}, { max: this.max }, config);
            params.range = this.__getRange(params);
            this.$wrap.append(tmpl.wrap(params));
            this.__bindEvent();
        },
        __bindEvent: function () {
            var _this = this;

            this.$wrap.on('click', '.J-pager-item', function () {
                var isDisabled = ($(this).hasClass('page-disabled') || $(this).hasClass('page-current'));
                var pageNum = $(this).data('current');

                if (!isDisabled) {
                    _this.reset({ pageNum: pageNum });

                    _this.config.callback && _this.config.callback(pageNum);
                }
            })

            this.$wrap.on('click', '.J-pager-jump', function () {
                var $input = $('.J-pager-input', _this.$wrap);
                var pageNum = $input.val();

                if (!pageNum) {
                    $input.focus();
                    return false;
                }

                if (!/^\d*$/.test(pageNum)) {
                    $input.val('').focus();
                } else {
                    var num = parseInt(pageNum);
                    pageNum = num < 1 ? 1 : (num > _this.max ? _this.max : num);
                    $input.val('');
                    _this.reset({ pageNum: parseInt(pageNum) });

                    _this.config.callback && _this.config.callback(pageNum);
                }
            })
        },
        __getRange: function (params) {
            var cur = params.pageNum;
            var max = params.max;
            var range = [];

            if (max > 9) {
                if (cur < 5) {
                    range = [2, 8];
                } else if (cur > max - 4) {
                    range = [max - 7, max - 1];
                } else {
                    range = [cur - 3, cur + 3];
                }
            } else {
                range = [2, max - 1]
                console.log(max)
            }

            return range;
        },
        reset: function (params) {
            var newParams = $.extend({}, this.config, params);
            this.max = newParams.max = Math.ceil(newParams.total / newParams.pageSize);
            newParams.range = this.__getRange(newParams);

            this.$wrap.empty().append(tmpl.wrap(newParams));
        }
    }

    window.Pager = Pager;


}.call(this);