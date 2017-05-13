/**
 * Created by vitiv on 5/13/17.
 */

function Game(word) {
    this._word = word;
    this._badGuesses = new Set();
    this._move = 0;

    this._initCanvas();
    this._initAnimations();
    this._initBlanks();
    this._updateMoves();
    $(document).keydown(this._handleKey.bind(this));
}

alphabet = new Set('qwertyuiopasdfghjklzxcvbnm');

Game.prototype._handleKey = function (e) {
    var key = e.key.toLowerCase();
    if(alphabet.has(key)) {
        if(!(new Set(this._word).has(key)) ) {
            // bad guess
            if(!this._badGuesses.has(key)) {
                this._badGuesses.add(key);
                this._drawArray[this._move]();
                $('#bad-guesses').append($('<li>' + key + '<\li>'));
                this._move++;
                this._updateMoves();
            }
        }
        else {
            for (var i = 0; i < this._word.length; i++) {
                if(this._word[i] === key) {
                    $($('#word').children()[i]).html(key);
                }
            }
        }
    }
};

Game.prototype._updateMoves = function() {
    $('#lives-left').html(this._drawArray.length - this._move);
};

Game.prototype.constructor = Game;

Game.prototype._initBlanks = function() {
    word = $('#word');

    for (var i = 0; i < this._word.length; i++) {
        word.append($('<li>_</li>'));
    }
};

Game.prototype._initCanvas = function () {
    myStickman = $("#stickman")[0];
    context = myStickman.getContext('2d');
    context.beginPath();
    context.strokeStyle = "#000";
    context.lineWidth = 3;

};

Game.prototype._initAnimations = function() {
    head = function () {
        myStickman = $("#stickman")[0];
        context = myStickman.getContext('2d');
        context.beginPath();
        context.arc(60, 25, 10, 0, Math.PI * 2, true);
        context.stroke();
    };

    draw = function (pathFromx, pathFromy, pathTox, pathToy) {
        myStickman = $("#stickman")[0];
        context = myStickman.getContext('2d');
        context.moveTo(pathFromx, pathFromy);
        context.lineTo(pathTox, pathToy);
        context.stroke();
    };

    frame1 = function () {
        draw(0, 150, 150, 150);
    };

    frame2 = function () {
        draw(10, 0, 10, 600);
    };

    frame3 = function () {
        draw(0, 5, 70, 5);
    };

    frame4 = function () {
        draw(60, 5, 60, 15);
    };

    torso = function () {
        draw(60, 36, 60, 70);
    };

    rightArm = function () {
        draw(60, 46, 100, 50);
    };

    leftArm = function () {
        draw(60, 46, 20, 50);
    };

    rightLeg = function () {
        draw(60, 70, 100, 100);
    };

    leftLeg = function () {
        draw(60, 70, 20, 100);
    };

    this._drawArray = [rightLeg, leftLeg, rightArm, leftArm, torso, head, frame4, frame3, frame2, frame1].reverse();
};


$(document).ready(function() {
    var game = new Game(word);
});
