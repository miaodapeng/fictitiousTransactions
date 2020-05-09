var defaults = {
    el: '.J-suggest',
    url: '',
    data: null,
    type: 'suggest', //history,suggest
    mode: 'async', //sync or async,
    parseData: null,
    params: 'word'
}

var wrapTmpl = '<div class="suggest-list-wrap J-suggest-wrap"></div>'

var upTimeout = null;

var Suggest = function (config) {
    this.config = $.extend(true, {}, defaults, config);
    this.__init();
    return this;
};

Suggest.prototype = {
    constructor: Suggest,
    __init: function () {
        this.$el = $(this.config.el);
        this.__initTmpl();

        this.$suggestWrap = this.$el.siblings('.J-suggest-wrap');

        this.__bindEvent();
    },
    __initTmpl: function () {
        this.$el.after(wrapTmpl);
    },
    __bindEvent: function () {
        var _this = this;

        this.$el.on('keyup', function (e) {
            _this.__getData();
        })

        this.$el.on('focus', function (e) {
            _this.__getData();
        })

        this.$el.on('click', function (e) {
            e.stopPropagation();
        })

        this.$suggestWrap.on('click', '.J-item-disabled', function (e) {
            e.stopPropagation();
        })

        this.$suggestWrap.on('click', '.J-suggest-item', function (e) {
            _this.$el.val($(this).data('value'));
            if (_this.config.callback) {
                _this.config.callback($(this).data('params'))
            }
        })

        $(document).click(function () {
            _this.$suggestWrap.hide();
        })
    },
    __getData: function () {
        var _this = this;
        var val = $.trim(this.$el.val());
        upTimeout && clearTimeout(upTimeout);
        if (val) {
            upTimeout = setTimeout(function () {
                if (_this.config.mode === 'sync') {
                    _this.__getSyncData();
                } else if (_this.config.mode === 'async') {
                    _this.__getAsyncData();
                }
            }, 200);
        } else {
            this.$suggestWrap.empty().hide();
        }
    },
    __getSyncData: function (word) {
        var _this = this;
        if (!this.config.data) {
            window.console && console.log('没有初始数据');
        } else {
            var list = _this.__search(word);

            this.__showList(list);
        }
    },
    __getAsyncData: function () {
        var params = {};
        var _this = this;
        params[this.config.params] = $.trim(this.$el.val());
        $.ajax({
            url: this.config.url,
            async: false,
            data: params,
            dataType: 'json',
            success: function (data) {
                if (data && data.code === 0) {
                    if (_this.config.parseData) {
                        data = _this.config.parseData(data);
                    }
                    _this.__showList(data);
                }
            }
        })
    },
    __showList: function (list) {
        var _this = this;
        this.$suggestWrap.empty();
        // var word = word.replace(//)

        if (list.length) {
            $.each(list, function (i, item) {
                var disabled = item.disabled ? 'disabled J-item-disabled' : 'J-suggest-item';
                var param = item.data ? 'data-params="' + item.data + '"' : '';
                var word = item.word;
                if(_this.config.type = 'suggest'){
                    var searchWord = $.trim(_this.$el.val());
                    var regx = new RegExp(searchWord, 'g');
                    word = word.replace(regx, '<strong>' + searchWord + '</strong>');
                }
                _this.$suggestWrap.append('<div class="suggest-item ' + disabled + '" ' + param + ' data-value="' + item.word + '">' + word + '</div>');
            })

            this.$suggestWrap.show();
        } else {
            this.$suggestWrap.hide();
        }

    },
    __search: function (word) {
        var list = [];
        $.each(this.config.data, function (i, item) {
            list.push(item)
        })

        return list;
    }
};


// exports
if (typeof define === "function") { // amd & cmd
    define(function () {
        return Suggest;
    });
} else if (typeof module !== "undefined" && "exports" in module) { // commonJS
    module.exports = Suggest;
} else { // global
    window.Suggest = Suggest;
}