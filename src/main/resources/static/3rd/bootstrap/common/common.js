
/**
 * AJAX 호출.
 * @param url 호출 URL
 * @param data Params
 * @param fn callBack Function
 * @param option Option
 * @returns
 */
function callAjax(url, data, fn, option){
	if(option == undefined) {
		option = {
			'data': data,
			'method': 'POST',
			'success': function (data, textStatus, jqXHR) {
				if(fn) {
					fn(data, textStatus, jqXHR);
				}
			},
			'error': function () {
				alert('error');
			}
		}
	} else {
		option['data'] = data;
	}

	jQuery.ajax(url, option);
}

/**
 * Ajax 동기 호출
 * @param url
 * @param data
 * @returns
 */
function callSyncAjax(url, data) {
	var o;
	var option = {
			'data' : data,
			'method' : 'POST',
			'async' : false,
			'success' : function(data, textStatus, jqXHR){
				//o = jQuery.parseJSON(data);
				o = data;
			},
			'error' : function(){
				alert('error');
			}
	}
	jQuery.ajax(url, option);
	return o;
}