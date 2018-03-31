function receiveMessage(type, value) {
    type = convertFromCharCodes(type);
    value = convertFromCharCodes(value);
    var s = (type + " --> " + value);
    getHtmlElement('debug').innerHTML += '<br />' + htmlspecialchars(s);
}

function convertFromCharCodes(s) {
    var nums = s.split(' ');
    var length = nums.length;
    var output = [];
    for (var i = 0; i < length; ++i) {
        output.push(String.fromCharCode(parseInt(nums[i])));
    }
    return output.join('');
}

function sendMessage(type, value) {
    JavaScriptBridge.onSendNativeMessage(type, value);
}
