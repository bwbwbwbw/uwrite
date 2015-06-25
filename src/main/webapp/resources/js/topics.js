(function() {
  function throttle(fn, threshhold, scope) {
    threshhold || (threshhold = 250);
    var last,
        deferTimer;
    return function () {
      var context = scope || this;

      var now = +new Date,
          args = arguments;
      if (last && now < last + threshhold) {
        // hold on to it
        clearTimeout(deferTimer);
        deferTimer = setTimeout(function () {
          last = now;
          fn.apply(context, args);
        }, threshhold);
      } else {
        last = now;
        fn.apply(context, args);
      }
    };
  }

  var svgTemplate = $('.figure-template svg').clone();
  var svgFrom = Snap(svgTemplate[0]).select('path').attr('d');
  var svgTo = $('.figure-template').attr('data-path-hover');
  var speed = 330, easing = mina.backout;

  $(document).on('mouseenter', '.topic-card', function () {
    var path = Snap($(this).find('svg')[0]).select('path');
    path.animate( { 'path' : svgTo }, speed, easing );
  });

  $(document).on('mouseleave', '.topic-card', function () {
    var path = Snap($(this).find('svg')[0]).select('path');
    path.animate( { 'path' : svgFrom }, speed, easing );
  });

  $('.topic-card figcaption').each(function () {
    svgTemplate.clone().insertBefore($(this));
  });

  // 逐渐显示
  var cardHeight = 430;
  var cardOffset = 60;

  var lastTopRow = -1, lastBottomRow = -1;

  var cards = $('.topic-card');

  $(window).scroll(throttle(function(ev) {
    var rgBegin = window.scrollY;
    var rgEnd = window.scrollY + window.innerHeight;

    // 哪些在 rgBegin 之外？
    var rowIndex = Math.floor((rgBegin - cardOffset) / cardHeight);

    if (rowIndex < lastTopRow) {
      var beginIndex = rowIndex * 4;
      var endIndex = lastTopRow * 4 + 3;
      var oc = 0;
      for (var i = endIndex; i >= beginIndex; --i) {
        if (i >= 0) {
          if (cards[i].getAttribute('delay')) {
            clearTimeout(cards[i].getAttribute('delay'));
          }
          cards[i].setAttribute('delay', setTimeout(function (obj) {
            obj.removeAttribute('hide');
            obj.removeAttribute('delay');
          }, oc * 100, cards[i]));
          oc++;
        }
      }
      lastTopRow = rowIndex;
    } else if (rowIndex > lastTopRow) {
      var beginIndex = lastTopRow * 4;
      var endIndex = (rowIndex - 1) * 4 + 3;
      var oc = 0;
      for (var i = beginIndex; i <= endIndex; ++i) {
        if (i >= 0) {
          if (cards[i].getAttribute('delay')) {
            clearTimeout(cards[i].getAttribute('delay'));
          }
          cards[i].setAttribute('delay', setTimeout(function (obj) {
            obj.setAttribute('hide', 'top');
            obj.removeAttribute('delay');
          }, oc * 100, cards[i]));
          oc++;
        }
      }
      lastTopRow = rowIndex;
    }

    // 哪些在 rgEnd 之外？
    var rowIndex = Math.floor((rgEnd - cardOffset) / cardHeight);

    if (rowIndex < lastBottomRow) {
      var beginIndex = (rowIndex + 1) * 4;
      var endIndex = lastBottomRow * 4 + 3;
      var oc = 0;
      for (var i = beginIndex; i <= endIndex; ++i) {
        if (i >= 0) {
          if (cards[i].getAttribute('delay')) {
            clearTimeout(cards[i].getAttribute('delay'));
          }
          cards[i].setAttribute('delay', setTimeout(function (obj) {
            obj.setAttribute('hide', 'bottom');
            obj.removeAttribute('delay');
          }, oc * 100, cards[i]));
          oc++;
        }
      }
      lastBottomRow = rowIndex;
    } else if (rowIndex > lastBottomRow) {
      var beginIndex = lastBottomRow * 4;
      var endIndex = rowIndex * 4 + 3;
      var oc = 0;
      for (var i = beginIndex; i <= endIndex; ++i) {
        if (i >= 0) {
          if (cards[i].getAttribute('delay')) {
            clearTimeout(cards[i].getAttribute('delay'));
          }
          cards[i].setAttribute('delay', setTimeout(function (obj) {
            obj.removeAttribute('hide');
            obj.removeAttribute('delay');
          }, oc * 100, cards[i]));
          oc++;
        }
      }
      lastBottomRow = rowIndex;
    }
  }, 500));

  $(window).scroll();

})();