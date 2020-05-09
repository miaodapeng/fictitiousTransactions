// require jQuery, util, observe, template
/* global $, jQuery, util, observe, define */

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

var observe = function () {
	var keyIsObs = '__IS_OBSERVABLE__';
	var keyIsOnce = '__IS_ONCE_EVENT__';

	var slice = [].slice;

	var defineProperty;

	try {
		var obj = {};
		var test = 't';

		Object.defineProperty(obj, test, {
			value: 1,
			writable: false,
			enumerable: false,
			configurable: false
		});

		defineProperty = function (obj, key, value) {
			Object.defineProperty(obj, key, {
				value: value,
				writable: false,
				enumerable: false,
				configurable: false
			});
		}
	} catch (ex) {
		defineProperty = function (obj, key, value) {
			obj[key] = value;
		};
	}

	var eachName = function (names, callback) {
		names.replace(/([^\s\,]+)/g, callback);
	};

	/**
	 * impl
	 * @param [obj] {Object = {}} observed object
	 */
	var _observe = function (obj) {
		obj = obj || {};

		if (obj[keyIsObs]) {
			return obj;
		}

		defineProperty(obj, keyIsObs, true);

		var events = {};

		var getEvent = function (name) {
			return (events[name] = (events[name] || []));
		};

		var clearEvents = function (evts) {
			eachName(evts, function (name) {
				delete events[name]
			});
		};

		var clearAllEvents = function () {
			events = {};
		};

		var bindEvent = function (evts, fn, once) {
			if (typeof fn === 'function') {
				defineProperty(fn, keyIsOnce, !!once);

				eachName(evts, function (name) {
					getEvent(name).push(fn);
				});

			}
		};

		var execEvents = function (handles, params) {
			for (var i = 0; i < handles.length; i++) {
				var handle = handles[i];
				handle.apply(this, params);

				if (handle[keyIsOnce]) {
					handles.splice(i--, 1);
				}
			}
		};

		/**
		 * observe on some events
		 * @param evts {String} events list. eg "save close open"
		 * @param fn {Function} event handler
		 */
		defineProperty(obj, 'on', function (evts, fn) {
			bindEvent(evts, fn);
			return this;
		});

		/**
		 * observe on some events that once only excuted
		 * @param evts {String} events list. eg "save close open"
		 * @param fn {Function} event handler
		 */
		defineProperty(obj, 'one', function (evts, fn) {
			bindEvent(evts, fn, true);
			return this;
		});

		/**
		 * unobserve on some events
		 * @param [evts] {String} events list. eg "save close open"
		 * @param [fn] {Function} event handler
		 * @example
		 * 	obj.off(); // unobserve all events
		 *  obj.off('save'); // unobserve some type events
		 *  obj.off('save', fn); // unbind a handle with some type events
		 */
		defineProperty(obj, 'off', function (evts, fn) {
			if (typeof evts === 'string') {
				if (typeof fn === 'function') {
					eachName(evts, function (name) {
						var handles = getEvent(name);

						for (var i = 0; i < handles.length; i++) {
							if (handles[i] === fn) {
								handles.splice(i--, 1);
							}
						}
					});
				} else {
					clearEvents(evts);
				}
			} else {
				clearAllEvents();
			}

			return this;
		});

		/**
		 * observe on some events
		 * @param evts {String} events list. eg "save close open"
		 * @param fn {Function} event handler
		 */
		defineProperty(obj, 'fire', function (evts) {
			var _this = this;
			var params = slice.call(arguments, 1);

			if (typeof evts === 'string') {
				eachName(evts, function (name) {
					var handles = getEvent(name);
					execEvents.call(_this, handles, params);
				});
			} else {
				for (var evt in events) {
					if (events.hasOwnProperty(evt)) {
						var handles = events[evt];
						execEvents.call(_this, handles, params);
					}
				}
			}

			return this;
		});

		return obj;
	};

	return _observe;
}.call(this);


; void function () {
	var tmpl = util.templatify(util.tpl("<!--## select ##-->\r\n<div class=\"select\">\r\n\t<div class=\"select-title J-select-title\">\r\n\t\t<span class=\"select-label J-label\" style=\"display:none\"></span>\r\n\t\t<div class=\"select-selected\">\r\n\t\t\t<span class=\"J-text\"></span>\r\n\t\t</div>\r\n\t\t<div class=\"select-arrow J-arrow\">\r\n\t\t\t<i class=\"vd-icon icon-down\"></i>\r\n\t\t</div>\r\n\t</div>\r\n\t<div class=\"select-list J-select-list\">\r\n\t\t{{ if(isShowTab){ }}\r\n\t\t\t<div class=\"select-tabs-wrap\">\r\n\t\t\t\t<ul class=\"select-tabs J-select-tabs\">\r\n\t\t\t\t\t{{ util.each(tabs, function(tab){ }}\r\n\t\t\t\t\t\t<li class=\"select-tab J-select-tab\">{{=tab.name}}</li>\r\n\t\t\t\t\t{{ }); }}\r\n\t\t\t\t</ul>\r\n\t\t\t</div>\r\n\t\t{{ } }}\r\n\r\n\t\t{{ util.each(tabs, function(tab){ }}\r\n\t\t\t<ul class=\"select-list-wrap J-select-tab-cnt\">\r\n\t\t\t\t{{ util.each(tab.items, function(item){ }}\r\n\t\t\t\t\t{{ if(item.type === ITEM_TYPE.OPTION){ }}\r\n\t\t\t\t\t\t<li>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void('{{=item.name}}')\" cz-id=\"{{=item.id}}\" class=\"select-opt J-opt{{ if(item.css){ }} {{=item.css}}{{ } }}{{ if(item.disabled){ }} disabled{{ } }}\"{{ if(item.title){ }} title=\"{{=item.title}}\"{{ } }}>{{=item.name}}</a>\r\n\t\t\t\t\t\t</li>\r\n\t\t\t\t\t{{ }else if(item.type === ITEM_TYPE.GROUP){ }}\r\n\t\t\t\t\t\t<li class=\"select-group{{ if(isShowTab){ }} select-multab-group{{}}}\">\r\n\t\t\t\t\t\t\t<dl>\r\n\t\t\t\t\t\t\t\t<dt class=\"select-opt-label\"{{ if(item.title){ }} title=\"{{=item.title}}\"{{ } }}>{{=item.name}}</dt>\r\n\t\t\t\t\t\t\t\t{{ util.each(item.children, function(child){ }}\r\n\t\t\t\t\t\t\t\t\t<dd class=\"select-opt-val\"><a href=\"javascript:void('{{=child.name}}')\" cz-id=\"{{=child.id}}\" cz-pid=\"{{=item.id}}\" class=\"select-opt J-opt{{ if(child.css){ }} {{=child.css}}{{ } }}{{ if(item.disabled){ }} disabled{{ } }}\"{{ if(child.title){ }} title=\"{{=child.title}}\"{{ } }}>{{=child.name}}</a></dd>\r\n\t\t\t\t\t\t\t\t{{ }) }}\r\n\t\t\t\t\t\t\t</dl>\r\n\t\t\t\t\t\t</li>\r\n\t\t\t\t\t{{ } }}\r\n\t\t\t\t{{ }); }}\r\n\t\t\t</ul>\r\n\t\t{{ }); }}\r\n\t</div>\r\n</div>\r\n<!--## end ##-->\r\n\r\n<!--## title ##-->\r\n{{=name}}\r\n<!--## end ##-->"));

	// 标识符起始名
	var SIGN_NAME = 'select-';
	// 组件标识属性名
	var COMPONENT_NAME = 'select-duck';
	// 多tab标识符, 属性格式："tabname:显示文本"
	var MULTIPLE_TABS = 'select-tab';
	// tab名与文本分隔符
	var SEPARATOR_TAB = '::';
	// 自定义样式属性名
	var CSS_NAME = 'select-css';
	// 老版本支持的样式名
	var CSS_NAME_BACKUP = 'cz-css';
	// 自定义select标签属性名
	var LABEL_NAME = 'select-label';
	// 老版本支持的标签名
	var LABEL_NAME_BACKUP = 'cz-label';
	// 自定义数据
	var CUSTOM_DATA = 'select-';

	// 实例列表
	var instances = {};

	// 数据来源类型
	var SOURCE_TYPE = {
		ELEMENT: 'el',
		LOCAL_DATA: 'data',
		REMOTE: 'remote',
		DEFAULT: 'el'
	};

	// 标签类型
	var ITEM_TYPE = {
		OPTION: 'option',
		GROUP: 'group'
	};

	// 选项列表显示位置
	var DIRECTION = {
		UP: 'up',
		DOWN: 'down'
	};

	var defaults = {
		// 数据源元素
		source: null,
		// 数据类型
		stype: SOURCE_TYPE.DEFAULT,
		// 下拉列表显示位置
		dir: DIRECTION.DOWN,
		// 标签名
		label: '',
		// 模板
		tmpl: tmpl,
		// 空选项数据
		emptySelect: {
			name: '请选择',
			value: '',
			id: '__EMPTY_OPTION__'
		},
		// 特别样式列表
		css: {
			// 获焦
			focus: 'focus',
			// 展开
			open: 'open',
			// 选中项
			selected: 'selected',
			// 禁用项
			disabled: 'disabled',
			// 显示在上方
			up: 'top',
			// 显示在下方
			down: '',
			// 其他添加在外层的样式
			wrap: '',
			// 活动的tab标题
			active: 'active'
		},
		// 元素列表
		elems: {
			// 标题
			title: '.J-select-title',
			// 选中文本
			text: '.J-text',
			// 标签名
			label: '.J-label',
			// 箭头
			arrow: '.J-arrow',
			// 列表父节点
			list: '.J-select-list',
			// 选项
			option: '.J-opt',
			// preventDefault 忽略元素
			ignore: '.J-ignore-prevent',
			// 分页标题
			tabs: '.J-select-tab',
			// 分页内容
			tabContents: '.J-select-tab-cnt'
		}
	};

	/**
	 * 伪下拉框
	 * 事件列表：select, change, add, del, focus, blur
	 * 方法列表: selectId, val, select, empty, addOption, delOption, open, close, isOpen, focus, blur, isFocus, dispose
	*/
	var Select = function (config) {
		this.config = util.extend(true, {}, defaults, config);

		return this.__init() || this;
	};

	Select.prototype = {
		constructor: Select,
		__init: function () {
			this._ = {};
			this.elems = {};
			this._.baseId = 0;
			this._.prevSelected = null;
			this._.selected = null

			this._.isOpen = false;
			this._.isFocus = false;

			this.elems.$source = $(this.config.source).first();

			if (!this.elems.$source.length) {
				return null;
			}

			if (this.elems.$source.attr(COMPONENT_NAME)) {
				return instances[this.elems.$source.attr(COMPONENT_NAME)];
			}

			observe(this);

			// 分页机制
			if (this.elems.$source.attr(MULTIPLE_TABS)) {
				var tabsattr = this.elems.$source.attr(MULTIPLE_TABS).split(SEPARATOR_TAB);
				if (tabsattr.length && tabsattr[0]) {
					this._.tabname = tabsattr[0];
					this._.istabs = true;
					this.elems.$source = this.__fakeSelect($('select[' + MULTIPLE_TABS + '^="' + tabsattr[0] + SEPARATOR_TAB + '"]'));
				}
			}

			// 组件标识符
			this.sid = SIGN_NAME + (new Date().valueOf() + util.random(1, 1000)).toString(16);
			this.elems.$source.attr(COMPONENT_NAME, this.sid);
			// 加入实例列表
			instances[this.sid] = this;

			// 解析数据
			// 选项列表
			var resolvedData = this.__resolveSource();
			this._.tabs = resolvedData.tabs;
			this._.items = resolvedData.items;
			this._.isDisabled = !!resolvedData.disabled;
			// 纯可选项列表
			this._.plainItems = this.__plainItems(this._.items);
			// 默认选项
			// 无已选中项则设置第一项为选中
			var defaultOption = this._.defaultSelected || this.__firstOpt() || this.config.emptySelect;

			// 外层样式设置
			var wrapCss = this.elems.$source.attr(CSS_NAME) || this.elems.$source.attr(CSS_NAME_BACKUP) || '';
			this.config.css.wrap = $.trim(this.config.css.wrap + " " + wrapCss);

			// 下拉框标签名
			this.config.label = this.elems.$source.attr(LABEL_NAME) || this.elems.$source.attr(LABEL_NAME_BACKUP) || this.config.label || '';

			this.config.tmpl = util.extend(true, {}, tmpl, this.config.tmpl);

			//
			this.render();
			this.__bindEvents();

			// init
			this.elems.$source.hide();
			this.elems.$source.first().before(this.elems.$select);
			// 初始化选中的tab
			this.__initTab();

			// 设置标签名
			this.label(this.config.label);
			// 默认选中
			this._select(defaultOption, true);
			// 设置选中样式
			this.__changeSelectedCss(this._.selected, this._.prevSelected);
			// 默认禁用状态
			this._disabled(this._.isDisabled);
			this.close();
		},
		render: function () {
			// 若是重新渲染的，移除原有节点与事件
			this.elems.$select && this.elems.$select.remove();

			this.elems.$select = $(this.config.tmpl.select(util.extend(true, {}, {
				tabs: this._.tabs,
				isShowTab: !!this._.istabs,
				//items: this._.items,
				ITEM_TYPE: ITEM_TYPE
			})));

			this.elems.$title = $(this.config.elems.title, this.elems.$select);
			this.elems.$text = $(this.config.elems.text, this.elems.$title);
			this.elems.$label = $(this.config.elems.label, this.elems.$title);
			this.elems.$arrow = $(this.config.elems.arrow, this.elems.$title);
			this.elems.$list = $(this.config.elems.list, this.elems.$select);
			this.elems.tabs = $(this.config.elems.tabs, this.elems.$select);
			this.elems.tabContents = $(this.config.elems.tabContents, this.elems.$select);

			// 设置显示方向的样式
			var dirClass = '';

			if (this.config.dir === DIRECTION.UP) {
				dirClass = this.config.css.up;
			} else if (this.config.dir === DIRECTION.DOWN) {
				dirClass = this.config.css.down;
			}

			this.elems.$select.addClass(dirClass);

			// 其他外层样式
			this.elems.$select.addClass(this.config.css.wrap);
		},
		__uiEvents: function () {
			var _this = this;

			var stop = function (e) {
				e.stopPropagation();

				// 除不做 preventDefault 限制的设置的节点，一律阻止默认行为
				if (!($(e.target).is(_this.config.elems.ignore) || ($(e.target).parents(_this.config.elems.ignore).length && $(e.currentTarget).find(_this.config.elems.ignore).length))) {
					e.preventDefault();
				}
			};

			this.elems.$select.on('click', stop);

			// 点击页面 则关闭展开项
			$(document).on('click', function () {
				_this.close();
				_this.blur();
			});

			// 点击标题 展开/收缩列表，获得焦点
			this.elems.$title.on('click', function () {
				var isopen = _this.isOpen();
				$(document).trigger('click');
				_this.focus();

				if (isopen) {
					_this.close();
				} else {
					_this.open();
				}
			});

			// 点击选项
			this.elems.$list.on('click', this.config.elems.option + ':not(.' + this.config.css.disabled + ')', function (e) {
				_this.focus();

				var id = e.target.getAttribute('cz-id');
				var pid = e.target.getAttribute('cz-pid');

				var opts = _this._.items;
				var group = null;
				var children = null;
				var opt = null;

				if (pid) { // 分组内的选项
					group = util.filter(opts, function (o) { return (o.id + '') === pid })[0];
				}

				if (id) {
					opt = util.filter(group && group.children || opts, function (o) { return (o.id + '') === id })[0];
				}

				_this._select(opt);
			});


			// 选中tab
			this.elems.tabs.on('click', function () {
				_this.selectTab(_this.elems.tabs.index(this));
			});
		},
		__bindEvents: function () {
			var _this = this;

			this.__uiEvents();

			this.on('select', function (selected) {
				this.close();
			});

			// 新增或删除选项 需要重新渲染并绑定事件
			this.on('add del disabledOption', function () {
				// 重新解析纯可选项数据列表
				this._.plainItems = this.__plainItems(this._.items);

				this.render();
				this.__uiEvents();

				this.elems.$source.first().before(this.elems.$select);
				//
				this._select(this._.selected, "silent");
			});

			// 禁用控件后，关闭弹窗, 失焦
			this.on('disabled', function (disable) {
				if (disable) {
					this.close();
					this.blur();
				}
			});

			// 当前对象聚焦，则其他对象全部收缩列表并失焦
			this.on('focus', function () {
				util.each(instances, function (ins) {
					if (ins !== _this) {
						ins.close();
						ins.blur();
					}
				});
			});

			// 选中项变更后，同步变更原select元素，并触发原来绑定在元素上的事件
			this.on('change', function (opt) {
				this.elems.$source.val(opt.value);
				this.elems.$source.change();
			});

			// 去除原有选项文本样式，添加新样式
			this.on('change', function (selected, prev) {
				this.__changeSelectedCss(selected, prev);
			});
		},
		__changeSelectedCss: function (selected, prev) {
			if (prev && prev.css) {
				this.elems.$text.removeClass(prev.css);
			}

			if (selected.css) {
				this.elems.$text.addClass(selected.css);
			}
		},
		// 选中数据
		_select: function (item, silent) {
			var isChange = !this._.selected || (this._.selected.id !== item.id);
			this._.prevSelected = this._.selected;
			this._.selected = item;
			// 修改选中文本
			this.elems.$text.html(this.config.tmpl.title(item));

			// 设置选中样式
			$(this.config.elems.option, this.elems.$list).removeClass(this.config.css.selected)
				.filter('[cz-id=' + item.id + ']').addClass(this.config.css.selected);

			// 选中对应的tab页
			this.selectTab(item.tabid);

			if (!silent) {
				this.fire('select', item, this._.prevSelected);

				if (isChange) {
					this.fire('change', item, this._.prevSelected);
				}
			}
		},
		// 选中数据项
		select: function (item, silent) {
			this._select(item, silent);
		},
		// 选中值
		val: function (val, silent) {
			if (typeof val !== 'undefined') {
				var item = util.filter(this._.plainItems, function (item) { return item.value === val })[0];

				if (item) {
					this._select(item, silent);
				}
			} else {
				return this._.selected && this._.selected.value;
			}
		},
		// 选中id
		selectId: function (id, silent) {
			var item = util.filter(this._.plainItems, function (item) { return (item.id + '') === (id + '') })[0];

			if (item) {
				this._select(item, silent);
			}
		},
		// 清空选中
		empty: function () {
			this._select(this.config.emptySelect, "silent");
			this.fire('empty');
		},
		// 设置伪下拉标签
		label: function (text) {
			this.elems.$label.html(text);

			if (text) {
				this.elems.$label.show();
			} else {
				this.elems.$label.hide();
			}
		},
		// 展开列表
		open: function () {
			if (!this.isOpen() && !this.isDisabled()) {
				this.elems.$select.addClass(this.config.css.open);
				this._.isOpen = true;
				this.fire('open');
			}
		},
		// 关闭列表
		close: function () {
			if (this.isOpen() && this.elems.$select) {
				this.elems.$select.removeClass(this.config.css.open);
				this._.isOpen = false;
				this.fire('close');
			}
		},
		// 是否展开状态
		isOpen: function () {
			return this._.isOpen;
		},
		// 聚焦
		focus: function () {
			if (!this.isFocus() && !this.isDisabled() && this.elems.$select) {
				this._.isFocus = true;
				this.elems.$select.addClass(this.config.css.focus);
				this.fire('focus');
			}
		},
		// 失焦
		blur: function () {
			if (this.isFocus() && this.elems.$select) {
				this._.isFocus = false;
				this.elems.$select.removeClass(this.config.css.focus);
				this.fire('blur');
			}
		},
		// 是否聚焦状态
		isFocus: function () {
			return this._.isFocus;
		},
		// 控件禁用
		_disabled: function (disable, silent) {
			disable = !!disable;
			this._.isDisabled = disable;
			this.elems.$select[disable ? 'addClass' : 'removeClass'](this.config.css.disabled);
			this.elems.$source.attr('disabled', disable);
			!silent && this.fire('disabled', disable);
		},
		// 控件禁用
		disabled: function (disable) {
			this._disabled(disable);
		},
		// 是否被禁用
		isDisabled: function () {
			return !!this._.isDisabled;
		},
		// 设置选项的禁用状态
		_disabledOption: function (item, disable, silent) {
			if (item) {
				item.disabled = !!disable;
				!silent && this.fire('disabledOption', item);
			}
		},
		// 设置禁用状态
		disabledOption: function (item, disable) {
			var _this = this;

			if (typeof item === 'function') {
				var items = item.call(this, this._.items);

				if (items.length) {
					$.each(items, function (i, item) {
						_this._disabledOption(item, !!disable);
					});
				}
			} else {
				this._disabledOption(item, !!disable);
			}
		},
		// 依据option id禁用
		disabledOptionById: function (id, disable) {
			var item = util.filter(this._.plainItems, function (item) { return (item.id + '') === (id + '') })[0];

			if (item) {
				this._disabledOption(item, !!disable);
			}
		},
		// 依据值 禁用
		disabledByVal: function (val, disable) {
			var _this = this;
			var items = util.filter(this._.plainItems, function (item) { return (item.value + '') === (val + '') });

			if (items.length) {
				$.each(items, function (i, item) {
					_this._disabledOption(item, !!disable);
				});
			}
		},
		// 增加选项
		addOption: function (name, value, type, attrs, tabid) {
			tabid = tabid || 0;

			if (!this._.tabs[tabid]) {
				tabid = 0;
			}

			var item = {
				id: this._.baseId++,
				name: name,
				value: value,
				type: type === ITEM_TYPE.GROUP ? ITEM_TYPE.GROUP : ITEM_TYPE.OPTION,
				tabid: tabid
			};

			util.extend(true, item, attrs);
			this._.items.push(item);
			this._.tabs[tabid].items.push(item);

			this.fire('add', item);

			return item;
		},
		// 删除选项
		delOption: function (id) {
			var delItem = util.filter(this._.items, function (o) { return (o.id + '') === (id + '') });

			if (delItem) {
				this._.items = util.filter(this._.items, function (o) { return (o.id + '') !== (id + '') });
				this._.tabs[delItem.tabid].items = util.filter(this._.tabs[delItem.tabid].items, function (o) { return (o.id + '') !== (id + '') });

				this.fire('del', delItem);
			}

		},
		// 销毁对象
		dispose: function () {
			var _this = this;
			// 延迟 以避免ui错误
			setTimeout(function () {
				// 解除所有事件
				_this.off();

				_this.elems.$source && _this.elems.$source.show();
				_this.elems.$select && _this.elems.$select.remove();

				// 移除组件标识
				_this.elems.$source && _this.elems.$source.removeAttr(COMPONENT_NAME);
				// 移除引用实例
				delete _this.elems.$source
				delete _this.elems.$select;
				delete _this.elems.$title;
				delete _this.elems.$text;
				delete _this.elems.$label;
				delete _this.elems.$arrow;
				delete _this.elems.$list;
				delete _this.elems.tabs;
				delete _this.elems.tabContents;
				delete _this._.items;
				delete _this._.tabs;

				delete instances[_this.sid];
			}, 0);
		},
		__firstOpt: function () {
			return this._.plainItems[0] || null;
		},
		__getCustomAttrs: function (el, cache) {
			var attrs = el.attributes;

			for (var i = 0; i < attrs.length; i++) {
				var name = attrs[i].name;

				if (name.indexOf(CUSTOM_DATA) === 0) {
					var name2 = name.substr(CUSTOM_DATA.length);
					var val = el.getAttribute(name);

					try {
						!/^\s*$/.test(val) && (val = new Function('return ' + val)());
					} catch (ex) { }

					cache[name2] = val;
				}
			}

			return cache;
		},
		__gpAttrs: function (gp) {
			var _this = this;

			var item = {
				id: this._.baseId++,
				type: ITEM_TYPE.GROUP,
				name: gp.getAttribute('label'),
				title: gp.getAttribute('title'),
				children: []
			};

			this.__getCustomAttrs(gp, item);
			// 默认tabid
			item.tabid = item.tabid || 0;

			$(gp).children().each(function (i, opt) {
				var child = _this.__optAttrs(opt);
				child.pid = item.id;

				item.children.push(child);
			});

			return item;
		},
		__optAttrs: function (opt) {
			var item = {
				id: this._.baseId++,
				type: ITEM_TYPE.OPTION,
				name: opt.innerHTML,
				value: opt.value,
				disabled: opt.disabled,
				title: opt.getAttribute('title')
			};

			this.__getCustomAttrs(opt, item);

			//console.log(item.name, opt.getAttribute('selected'))
			/*var selected = opt.getAttribute('selected');
			if(selected !== null || selected === 'selected'){
				this._.defaultSelected = item;
			}*/

			if (opt.selected) {
				this._.defaultSelected = item;
			}

			return item;
		},
		// 解析数据
		__resolveSource: function () {
			var _this = this;
			var tabs = [];
			var items = [];

			this.elems.$source.children().each(function (i, el) {
				var NAME = el.tagName.toUpperCase();

				var item = null;

				if (NAME === 'OPTION') {
					item = _this.__optAttrs(el);
				} else if (NAME === 'OPTGROUP') {
					item = _this.__gpAttrs(el);
				}

				items.push(item);
			});

			$.each(items, function (i, item) {
				var id = item.tabid || 0;
				var tab = tabs[id];

				if (!tab) {
					tab = tabs[id] = {
						items: [],
						name: item.tabname
					};
				}

				delete item.tabname;
				tab.items.push(item);
			});

			return {
				tabs: tabs,
				items: items,
				disabled: this.elems.$source[0].disabled
			};
		},
		__plainItems: function (items) {
			var opts = [];

			if (items.length) {
				util.each(items, function (item) {
					if (item.type === ITEM_TYPE.OPTION) {
						opts.push(item);
					} else if (item.type === ITEM_TYPE.GROUP) {
						[].push.apply(opts, item.children);
					}
				});
			}

			return opts;
		},
		__initTab: function () {
			this.selectTab(0);
		},
		// 选中分页
		selectTab: function (id) {
			id = id || 0;
			this.elems.tabs.removeClass(this.config.css.active);
			this.elems.tabs.eq(id).addClass(this.config.css.active);

			this.elems.tabContents.hide().eq(id).show();
		},
		// 仿造一个select 替代多个 select 用于提交，并在内部用于添加、删除等数据操作
		__fakeSelect: function (selects) {
			var $selects = $(selects);
			var opts = $selects.map(function (i, el) {
				var $el = $(el);

				var name = $el.attr(MULTIPLE_TABS).split(SEPARATOR_TAB)[1];

				return $el.children().clone(true).attr({
					'select-tabid': i,
					'select-tabname': name
				});
			}).toArray();

			var $select = $selects.filter('select[name]:first').clone(false).empty();

			if (!$select.length) {
				$select = $('<select>');
			}

			$.each(opts, function (i, opt) {
				$select.append(opt);
			});

			// 伪造后移除原元素，并加入新的元素
			$selects.first().before($select);
			$selects.remove();

			return $select;
		}
	};

	// 实例列表
	Select.instances = instances;

	// 应用到页面上
	Select.use = function (selector, options) {
		var ins = [];

		var tabs = {};

		$(selector || 'select').filter('select').filter(function (i, el) {
			// 同一个多tab的仅允许实例化一次
			var tabattr = $(el).attr(MULTIPLE_TABS);
			if (tabattr) {
				var tab = tabattr.split(SEPARATOR_TAB)[0];
				if (tab in tabs) {
					return false;
				} else {
					tabs[tab] = true;
					return true;
				}
			} else {
				return true;
			}

		}).each(function (i, el) {
			ins.push(new Select($.extend(true, {}, options, { source: el })));
		});

		return ins;
	};

	Select.unuse = function (selector) {
		var ins = [];
		var tabs = {};

		$(selector || 'select').filter('select').filter(function (i, el) {
			// 同一个多tab的仅允许实例化一次
			var tabattr = $(el).attr(MULTIPLE_TABS);
			if (tabattr) {
				var tab = tabattr.split(SEPARATOR_TAB)[0];
				if (tab in tabs) {
					return false;
				} else {
					tabs[tab] = true;
					return true;
				}
			} else {
				return true;
			}

		}).each(function (i, el) {
			// 如果实例化过，则启动解构
			if ($(el).attr(COMPONENT_NAME)) {
				(new Select({ source: el })).dispose();
			}
		});
	};

	// exports
	if (typeof define === "function") { // amd & cmd
		define(function () {
			return Select;
		});
	} else if (typeof module !== "undefined" && "exports" in module) { // commonJS
		module.exports = Select;
	} else { // global
		window.Select = Select;
	}
}.call(this);