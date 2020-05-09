; void function () {


    var tmpl = "<div class=\"vd-input-number-wrap\">\r\n    <div class=\"vd-number-btn disabled J-input-number-btn-left\">\r\n        <i class=\"vd-icon icon-deduct\"></i>\r\n    </div>\r\n    <div class=\"vd-number-input\">\r\n        <input type=\"text\" class=\"vd-input-text J-input-number-text\">\r\n    </div>\r\n    <div class=\"vd-number-btn J-input-number-btn-right\">\r\n        <i class=\"vd-icon icon-add\"></i>\r\n    </div>\r\n    <div class=\"vd-number-tip tip-hide J-input-number-tip\"></div>\r\n</div>";

    var defaults = {
        el: '',
        value: 1,
        max: 9999,
        name: '',
        onchange: null
    };

    var InputNumber = function (config) {
        this.config = $.extend({}, defaults, config);
        this.__init();
        return this;
    };

    InputNumber.prototype = {
        constructor: 'InputNumber',
        __init: function () {
            if (!this.config.el) {
                window.console && console.log('no el');
                return false;
            } else {
                this.$wrap = $(tmpl);
                $(this.config.el).after(this.$wrap);
                $(this.config.el).remove();
            }

            this.$btnLeft = $('.J-input-number-btn-left', this.$wrap);
            this.$btnRight = $('.J-input-number-btn-right', this.$wrap);
            this.$text = $('.J-input-number-text', this.$wrap);
            this.$tip = $('.J-input-number-tip', this.$wrap);

            this.$text.attr('name', this.config.name);
            this.$text.val(this.config.value);
            this.$tip.html('最大输入' + this.config.max);

            this.checkFlag = true;
            this.showTipTimeout = null;

            this.__checkBtn();
            this.__bindEvent();
        },
        __bindEvent: function () {
            var _this = this;

            this.$btnLeft.click(function () {
                var val = parseInt(_this.$text.val());

                if (val > 1) {
                    _this.$text.val(val - 1).trigger('change');
                    _this.__checkBtn();
                }
            })

            this.$btnRight.click(function () {
                var val = parseInt(_this.$text.val());

                if (val < _this.config.max) {
                    _this.$text.val(val + 1).trigger('change');
                    _this.__checkBtn();
                }
            })

            this.$text.on('compositionstart', function () {
                _this.checkFlag = false;
            })

            this.$text.on('compositionend', function () {
                _this.checkFlag = true;
            })

            this.$text.on('keyup', function () {
                $(this).trigger('change');
            })

            this.$text.on('change', function () {
                _this.__checkValue();
            })

            this.$text.on('blur', function () {
                _this.__checkValue(true);
            })
        },
        __checkBtn: function () {
            var val = parseInt(this.$text.val());
            if (val <= 1) {
                this.$btnLeft.addClass('disabled');
            } else {
                this.$btnLeft.removeClass('disabled');
            }

            if (val >= this.config.max) {
                this.$btnRight.addClass('disabled');
            } else {
                this.$btnRight.removeClass('disabled');
            }
        },
        __checkValue: function (blur) {
            var _this = this;

            if (this.checkFlag) {
                setTimeout(function () {
                    var val = $.trim(_this.$text.val());
                    if (val && !(/^\d*$/.test(val))) {
                        val = val.replace(/[^\d]+/g, '');
                    }

                    if (val && val != 0) {
                        val = parseInt(val);

                        if (val > _this.config.max) {
                            val = _this.config.max;
                            _this.showTipTimeout && clearTimeout(_this.showTipTimeout)
                            _this.$tip.removeClass('tip-hide').addClass('tip-show');
                            _this.showTipTimeout = setTimeout(function () {
                                _this.$tip.removeClass('tip-show').addClass('tip-hide');
                            }, 2000);
                        }
                    } else if (blur) {
                        val = 1;
                    }

                    _this.$text.val(val);
                    _this.__checkBtn();
                    _this.config.onchange && _this.config.onchange(_this.value());
                }, 10)
            }
        },
        value: function (num) {
            if (num) {
                if (!/^\d*$/.test(num)) {
                    window.console && console.log('params should be a number');
                } else {
                    this.$text.val(num);
                }
            } else {
                return this.$text.val();
            }
        }
    }

    window.InputNumber = InputNumber;

}.call(this);