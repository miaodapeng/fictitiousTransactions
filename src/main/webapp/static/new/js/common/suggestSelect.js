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


    var tmpl = TEMPFY("<!-- ## wrap ## -->\r\n<div class=\"select-suggest J-select-title\">\r\n    <div class=\"select-title\">\r\n        <span class=\"select-label J-label\" style=\"display:none\"></span>\r\n        <div class=\"select-selected\">\r\n            <span class=\"J-text {{if(multi){}}select-multi-text{{}}}\">\r\n                {{ if(multi){ }}<span class=\"select-multi-text-default\">{{=placeholder}}</span>{{ }else{ }}{{=placeholder}}{{ } }}</span>\r\n        </div>\r\n        <div class=\"select-arrow J-arrow\">\r\n            <i class=\"vd-icon icon-down\"></i>\r\n        </div>\r\n    </div>\r\n    <div class=\"select-list J-select-list\">\r\n        {{ if(search){ }}\r\n        <div class=\"select-list-input\">\r\n            <input type=\"text\" placeholder=\"{{=searchPlaceholder}}\" class=\"select-input-text J-suggest-select-input\">\r\n            <i class=\"input-search-icon vd-icon icon-search\"></i>\r\n        </div>\r\n        {{ } }}\r\n\r\n        <div class=\"select-loading J-select-loading\">\r\n\r\n        </div>\r\n        \r\n        <ul class=\"select-list-wrap base-form J-select-tab-cnt\">\r\n            {{ if(data.length){ }}\r\n            {{ if(multi && multiAll){ }}\r\n            <li>\r\n                <div class=\"input-checkbox\">\r\n                    <span class=\"select-opt input-wrap J-select-item-all\">\r\n                        <input type=\"checkbox\">\r\n                        <span class=\"input-ctnr\"></span>全部\r\n                    </span>\r\n                </div>\r\n            </li>\r\n            {{ } }}\r\n            {{ $.each(data, function(i, item){ }}\r\n            <li>\r\n                {{ if(multi){ }}\r\n                <div class=\"input-checkbox\">\r\n                    <span class=\"select-opt input-wrap J-select-item\" data-id=\"{{=item.value}}\" data-html=\"{{=item.label}}\">\r\n                        <input type=\"checkbox\" class=\"J-select-checkbox\" {{ if(item.checked){ }}checked{{ } }}>\r\n                        <span class=\"input-ctnr\"></span>{{=item.label}}\r\n                    </span>\r\n                </div>\r\n                {{ }else{ }}\r\n                <a href=\"javascript:void(0);\" class=\"select-opt J-select-item\" data-params='{{=JSON.stringify(item)}}' data-id=\"{{=item.value}}\" data-html=\"{{=item.label}}\">{{=item.label}}</a>\r\n                {{ } }}\r\n            </li>\r\n            {{ }); }}\r\n            {{ }else{ }}\r\n            <li>\r\n                <span class=\"select-opt disabled\">无结果</span>\r\n            </li>\r\n            {{ } }}\r\n        </ul>\r\n    </div>\r\n</div>\r\n<!-- ## end ## -->\r\n\r\n<!-- ## list ## -->\r\n{{ if(data.length){ }}\r\n{{ if(multi && multiAll && !isSearch){ }}\r\n<li>\r\n    <div class=\"input-checkbox\">\r\n        <span class=\"select-opt input-wrap J-select-item-all\">\r\n            <input type=\"checkbox\" {{ if(multiAllSelected){ }}checked{{ } }}>\r\n            <span class=\"input-ctnr\"></span>全部\r\n        </span>\r\n    </div>\r\n</li>\r\n{{ } }}\r\n{{ $.each(data, function(i, item){ }}\r\n<li>\r\n    {{ if(multi){ }}\r\n    <div class=\"input-checkbox\">\r\n        <span class=\"select-opt input-wrap J-select-item\" data-id=\"{{=item.value}}\" data-html=\"{{=item.label}}\">\r\n            <input type=\"checkbox\" class=\"J-select-checkbox\" {{ if(item.checked){ }}checked{{ } }}>\r\n            <span class=\"input-ctnr\"></span>{{=item.label}}\r\n        </span>\r\n    </div>\r\n    {{ }else{ }}\r\n    <a href=\"javascript:void(0);\" class=\"select-opt J-select-item\" data-params='{{=JSON.stringify(item)}}' data-id=\"{{=item.value}}\" data-html=\"{{=item.label}}\">{{=item.label}}</a>\r\n    {{ } }}\r\n</li>\r\n{{ }); }}\r\n{{ }else{ }}\r\n<li>\r\n    <span class=\"select-opt no-result\">无结果</span>\r\n</li>\r\n{{ } }}\r\n<!-- ## end ## -->\r\n\r\n<!-- ## text ## -->\r\n<span class=\"select-tag-label\">\r\n    {{=item.label}}\r\n    <i class=\"vd-icon icon-delete J-select-multi-del\" data-id=\"{{=item.value}}\"></i>\r\n</span>\r\n<!-- ## end ## -->");

    var defaults = {
        wrap: '.J-suggest-select-wrap',
        auto: false,
        placeholder: '请选择',
        data: [],
        async: false,
        url: '',
        needRefresh: false,
        input: '',
        multi: false,
        multiAll: true,
        multiAllSelected: false,
        onchange: null,
        search: true,
        dataparse: null,
        searchUrl: '',
        asyncSearch: false,
        asyncSearchName: 'keyWords',
        searchPlaceholder: ''
    };

    var SuggestSelect = function (config) {
        this.config = $.extend({}, defaults, config);
        this.__init();
        return this;
    }

    SuggestSelect.prototype = {
        constructor: 'SuggestSelect',
        __init: function () {
            this.$wrapper = $(this.config.wrap);
            this.$input = $(this.config.input);
            this.$wrapper.append(tmpl.wrap(this.config));
            this.canSearch = true;
            this.data = JSON.parse(JSON.stringify(this.config.data));
            this.listData = this.data;
            this.isSearch = false;
            this.multiAllSelected = this.config.multiAllSelected;
            this.refreshed = false;
            this.$loading = this.$wrapper.find('.J-select-loading');

            this.__bindEvent();
            this.__initDOM();
        },
        __bindEvent: function () {
            var _this = this;
            this.$wrapper.on('click', '.J-select-title', function (e) {
                e.stopPropagation();
                var isShow = _this.$wrapper.find('.J-select-title').hasClass('show');
                $(document).trigger('click');

                if (isShow) {
                    _this.__hideList();
                } else {
                    _this.__showList();
                    if (_this.config.asyncSearch) {
                        _this.__getAsyncSearchData();
                    } else {
                        if ((_this.config.async && !_this.config.needRefresh && !_this.refreshed) || (_this.config.async && _this.config.needRefresh)) {
                            _this.__showLoding();

                            setTimeout(function () {
                                var resData = _this.__getAsyncData();

                                _this.data = resData;
                                _this.listData = resData;
                                _this.__refresh(resData);
                                _this.__hideLoading();
                                _this.refreshed = true;
                            }, 10)
                        }
                    }

                }
            })

            this.$wrapper.on('click', '.J-select-list', function (e) {
                e.stopPropagation();
            })

            this.$wrapper.on('compositionstart', '.J-suggest-select-input', function () {
                _this.canSearch = false;
            })

            this.$wrapper.on('compositionend', '.J-suggest-select-input', function () {
                _this.canSearch = true;
            })

            this.$wrapper.on('keydown', '.J-suggest-select-input', function (e) {
                if (e.keyCode === 13 || e.keyCode === 108) {
                    return false;
                }
            })

            this.$wrapper.on('keyup', '.J-suggest-select-input', function (e) {
                var $this = $(this);

                if (_this.canSearch) {
                    if (_this.config.asyncSearch) {
                        _this.asyncSearchTimeout && clearTimeout(_this.asyncSearchTimeout);

                        _this.asyncSearchTimeout = setTimeout(function () {
                            _this.isSearch = !!($.trim($this.val()));
                            _this.__getAsyncSearchData($.trim($this.val()));
                        }, 100)
                    } else {
                        setTimeout(function () {
                            _this.isSearch = !!($.trim($this.val()));
                            _this.__search($.trim($this.val()));
                        }, 0)
                    }
                }
            })

            this.$wrapper.on('click', '.J-select-multi-del', function (e) {
                e.stopPropagation();
                var $check = _this.$wrapper.find('.J-select-item[data-id="' + $(this).data('id') + '"] input:checkbox')[0];
                $check && ($check.checked = false);
                _this.__setMulitiSelect($(this).data('id'));
            })

            this.$wrapper.on('click', '.J-select-item', function () {
                if (!_this.config.multi) {
                    _this.$wrapper.find('.J-text').html($(this).data('html'));
                    _this.$input.val($(this).data('id'));
                    _this.__hideList();
                } else {
                    $(this).find('input:checkbox')[0].checked = !$(this).find('input:checkbox')[0].checked;
                    _this.__setMulitiSelect($(this).data('id'));
                }

                _this.config.onchange && _this.config.onchange($(this).data('id'), $(this).data('params'));
            })

            this.$wrapper.on('click', '.J-select-item-all', function () {
                _this.multiAllSelected = !_this.multiAllSelected;
                $.each(_this.data, function (i, obj) {
                    _this.data[i].checked = _this.multiAllSelected;
                });

                _this.__setMulitiData();

                $(this).find('input:checkbox')[0].checked = _this.multiAllSelected;

                _this.$wrapper.find('.J-select-item input:checkbox').each(function () {
                    $(this)[0].checked = _this.multiAllSelected;
                });
            })

            $(document).click(function () {
                _this.__hideList();
            })
        },
        __hideList: function () {
            this.$wrapper.find('.J-select-title').removeClass('show');
            this.$wrapper.find('.J-select-list').hide();
        },
        __showList: function () {
            this.$wrapper.find('.J-select-title').addClass('show');
            this.$wrapper.find('.J-select-list').show();;
        },
        __search: function (word) {
            var _this = this;
            var word = $.trim(word);
            var resData = [];

            $.each(this.data, function (i, item) {
                if (item.label.indexOf(word) != -1) {
                    resData.push(item);
                }
            })

            this.listData = resData;

            _this.__refresh(resData);
        },
        __setMulitiSelect: function (id, refresh) {
            var _this = this;
            $.each(this.data, function (i, obj) {
                if (id && obj.value == id) {
                    _this.data[i].checked = !_this.data[i].checked;
                }
            });

            this.__setMulitiData(refresh);
        },
        __setMulitiData: function (refresh) {
            var ids = [], _this = this, $text = this.$wrapper.find('.J-text');
            $text.empty()
            $.each(this.data, function (i, item) {
                if (item.checked) {
                    $text.append(tmpl.text({ item: item }));
                    ids.push(item.value);
                }
            })

            if (!ids.length) {
                $text.append('<span class="select-multi-text-default">' + this.config.placeholder + '</span>')
            }

            if (this.config.multi && this.config.multiAll) {
                this.__chechSelectAll();
            }

            if (refresh) {
                this.__refresh(this.listData);
            }

            this.$input.val(ids.join('@'));
        },
        __getAsyncSearchData: function (word) {
            var data = {};
            var _this = this;
            data[_this.config.asyncSearchName] = word || '';
            $.ajax({
                url: _this.config.searchUrl,
                data: data,
                dataType: 'json',
                success: function (res) {
                    if (res.code == 0) {
                        var resData = [];
                        if (_this.config.dataparse) {
                            resData = _this.config.dataparse(res);
                        } else {
                            resData = res.data;
                        }
                        _this.__refresh(resData);
                    }
                }
            })
        },
        __chechSelectAll: function () {
            var all = true;
            $.each(this.data, function (i, item) {
                if (!item.checked) {
                    all = false;
                }
            })

            this.multiAllSelected = all;
            this.$wrapper.find('.J-select-item-all input:checkbox')[0] && (this.$wrapper.find('.J-select-item-all input:checkbox')[0].checked = all);
        },
        __refresh: function (data) {
            this.$wrapper.find('.J-select-tab-cnt').empty().append(tmpl.list({
                data: data,
                multi: this.config.multi,
                multiAll: this.config.multiAll,
                multiAllSelected: this.multiAllSelected,
                isSearch: this.isSearch
            }));
        },
        __initDOM: function () {
            var val = $.trim(this.$input.val());
            var _this = this;

            if (val) {
                if (this.config.async) {
                    this.data = this.__getAsyncData();
                }
                if (this.config.multi) {
                    $.each(val.split('@'), function (i, obj) {
                        _this.__setMulitiSelect(obj, true);
                    })
                    this.__refresh(this.data);
                } else {
                    $.each(this.data, function (i, obj) {
                        if (obj.value == val) {
                            _this.$wrapper.find('.J-text').html(obj.label);
                        }
                    })
                }

            }
        },
        __showLoding: function () {
            this.$loading.show();
            this.$wrapper.find('.J-select-tab-cnt').hide();
        },
        __hideLoading: function () {
            this.$loading.hide();
            this.$wrapper.find('.J-select-tab-cnt').show();
        },
        __getAsyncData: function () {
            var _this = this;
            var resData = [];
            var data = {};

            if (this.config.needRefresh) {
                data.t = new Date().valueOf();
            }

            $.ajax({
                url: _this.config.url,
                data: data,
                dataType: 'json',
                async: false,
                success: function (res) {
                    if (res.code == 0) {
                        if (_this.config.dataparse) {
                            resData = _this.config.dataparse(res);
                        } else {
                            resData = res.data;
                        }
                    }
                }
            })

            return resData;
        },
        onchange: function () {
            if (this.config.onchange) {
                this.config.onchange(this.$input.val());
            }
        }

    }

    window.SuggestSelect = SuggestSelect;


}.call(this);