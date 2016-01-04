$(function () {
    "use strict";

    var content = $('#content');
    var submitBtn = $('#submitBtn');
    var socket = atmosphere;
    var subSocket;
    var transport = 'websocket';

    var request = {
        url: document.location.toString() + 'macd',
        contentType: "application/json",
        transport: transport,
        trackMessageLength: true,
        reconnectInterval: 5000
    };

    request.onOpen = function(response) {
        submitBtn.removeAttr('disabled');
        transport = response.transport;

        request.uuid = response.request.uuid;
    };

    request.onMessage = function (response) {
        var message = response.responseBody;
        try {
            var json = atmosphere.util.parseJSON(message);
            console.log(json);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message);
            return;
        }
    };

    request.onReconnect = function(request, response) {
        content.html($('<p>', { text: 'Connection lost, trying to reconnect. Trying to reconnect.. '}));
        submitBtn.attr('disabled', 'disabled');
    };

    subSocket = socket.subscribe(request);

    submitBtn.click(function() {
        subSocket.push(atmosphere.util.stringifyJSON({
            token: "some_token",
            subscribe: [{marketCode: "XNYS", securityCode: "MSFT"}]
        }));
    });
});
