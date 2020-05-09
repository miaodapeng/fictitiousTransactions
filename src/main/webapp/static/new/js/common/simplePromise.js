var SimplePromise = function(){
	
var READY_STATE = {
	UNSTART: 0,
	RESOLVED: 1,
	REJECTED: 2,
	EXCEPTION: 3
};

var SimplePromise = function(promise){
	var _this = this;
	this.readyState = READY_STATE.UNSTART;
	this.__data;
	this.__err;
	this._resolveHandles = [];
	this._rejectHandles = [];
	this._catchHandles = [];

	setTimeout(function(){
		try{
			promise(function(data){
				_this.__resolve(data);
			}, function(err){
				_this.__reject(err);
			});
		}catch(ex){
			_this.__catch(ex);
		}
	}, 0);
};

SimplePromise.prototype = {
	__resolve: function(data){
		this.readyState = READY_STATE.RESOLVED;
		this.__data = data;
		this.__checkState();
	},
	__reject: function(err){
		this.readyState = READY_STATE.REJECTED;
		this.__err = err || "";
		this.__checkState();
	},
	__catch: function(ex){
		this.readyState = READY_STATE.EXCEPTION;
		this.__ex = ex;
		this.__checkState();
	},
	__checkState: function(){
		var handles = null;
		var data = null;

		switch(this.readyState){
			case READY_STATE.UNSTART: {}; break;
			case READY_STATE.REJECTED: {
				handles = this._rejectHandles;
				data = this.__data;
			}; break;
			case READY_STATE.RESOLVED: {
				handles = this._resolveHandles;
				data = this.__err;
			}; break;
			case READY_STATE.EXCEPTION: {
				handles = this._catchHandles;
				data = this.__ex;
			}; break;
			default:;
		}

		if(handles){
			this.__exec(handles, data);
		}
	},
	__exec: function(handles, data){
		if(handles && handles.length){
			while(handles.length){
				handles.shift().call(this, data);
			}
		}
	},
	then: function(fn){
		this._resolveHandles.push(fn);
		this.__checkState();

		return this;
	},
	reject: function(fn){
		this._rejectHandles.push(fn);
		this.__checkState();

		return this;
	},
	"catch": function(fn){
		this._catchHandles.push(fn);
		this.__checkState();

		return this;
	}
};

SimplePromise.delay = function(delay){
	delay = delay || 0;

	return new Promise(function(resolve){
		setTimeout(resolve, delay);
	});
};

return SimplePromise;

}.call(this);