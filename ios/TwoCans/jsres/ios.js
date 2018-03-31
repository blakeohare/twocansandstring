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

console.log = function(msg) {
    sendMessageImpl('L' + msg);
};

function sendMessage(type, value) {
    sendMessageImpl('M', type + ': ' + value);
}

function sendMessageImpl(prefix, payload) {
    window.webkit.messageHandlers.interop.postMessage(prefix + payload);
}
