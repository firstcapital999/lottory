/**
 * Created by 黄旭晖 on 2017/10/24.
 */

var lottery = {
    index: -1,	//当前转动到哪个位置，起点位置
    count: 0,	//总共有多少个位置
    timer: 0,	//setTimeout的ID，用clearTimeout清除
    speed: 20,	//初始转动速度
    times: 0,	//转动次数
    cycle: 50,	//转动基本次数：即至少需要转动多少次再进入抽奖环节 ，顺时针旋转
    prize: -1,	//中奖位置
    callback: null,
    init: function (id) {
        /**
         * 获取奖品数量
         */
        if ($("#" + id).find(".lottery-unit").length > 0) {
            var $lottery = $("#" + id);
            var $units = $lottery.find(".lottery-unit");
            this.obj = $lottery;
            this.count = $units.length;
            $lottery.find(".lottery-unit-" + this.index).addClass("active");
        }
    },
    roll: function () {
        /**
         * 每次旋转定位选中位置
         */
        var index = this.index;
        var count = this.count;
        var lottery = this.obj;
        //移除当前位置选中样式
        $(lottery).find(".lottery-unit-" + index).removeClass("active");
        index += 1;
        //如果默认的当前位置大于总奖品数量，则默认为第一个
        if (index > count - 1) {
            index = 0;
        }
        //添加下一个位置选中样式
        $(lottery).find(".lottery-unit-" + index).addClass("active");
        this.index = index;
        return false;
    },
    stop: function (index) {
        this.prize = index;
        return false;
    }
};

/**
 * 根据已转动的次数判断当前的速度和最终停止的位置
 * @returns {boolean}
 */
function roll() {
    lottery.times += 1;
    lottery.roll();
    if (lottery.times > lottery.cycle + 10 && lottery.prize == lottery.index) {
        clearTimeout(lottery.timer);
        lottery.prize = -1;
        lottery.times = 0;
        lottery.callback && lottery.callback();
        _selfObj.click = false;
    } else {

        if (lottery.times < lottery.cycle) {
            lottery.speed -= 10;
        }
        else if (lottery.times == lottery.cycle) {
            //这个给随机中奖位置，如果是后端传值，则不用。
//                        var index = Math.random()*(lottery.count)|0;
//                        lottery.prize = index;
            //兼容后端传中奖位置，如果是随机，则不用
            (this.count - 1 < this.prize) && (this.prize = 0);// 如果中奖位置大于总奖品数，则默认为第一个中奖
        }
        else {
            if (lottery.times > lottery.cycle + 10 && ((lottery.prize == 0 && lottery.index == 7) || lottery.prize == lottery.index + 1)) {
                lottery.speed += 110;
            } else {
                lottery.speed += 20;
            }
        }
        if (lottery.speed < 40) {
            lottery.speed = 40;
        }
        ;
        //console.log(lottery.times+'^^^^^^'+lottery.speed+'^^^^^^^'+lottery.prize);
        lottery.timer = setTimeout(roll, lottery.speed);
    }
    return false;
}

/*==================================上面为抽奖逻辑=====================================================*/
/**
 * 定义奖品位置
 * 1 :苹果7,2：苹果6，3：购物卡，4：玉如意，5：会员体验
 * @type {{1: number[], 2: number[], 3: number[]}}
 */
var awardObj = {
    1: [0, 3, 7, 10],
    2: [4, 6],
    3: [2],
    4: [8],
    5: [1, 5, 9, 11]
};

/**
 * 计算中奖位置，这个是根据需求来定。
 * awardId:奖品id，由后端返回，前端根据awardObj + 业务需求计算停留的位置。
 */
function calculateAwardPositon(awardId) {
    //console.log('选中框移动到的位置'+_selfObj.initIx)
    var awardPositon = 0; //默认第一个位置
    for (var i = 0; i < awardObj[awardId].length - 1; i++) {
        //console.log(awardObj[awardId][i])
        if (awardObj[awardId][i] > _selfObj.initIx) {
            awardPositon = awardObj[awardId][i]; //将距离自动位置的最近奖品位置返回
            break
        }
    }
    return awardPositon;
}

/**
 * 获取中奖位置
 * @param req
 */
function getAwardPostion(req) {
    clearTimeout(_selfObj.timerIx);
    _selfObj.data = req;
    lottery.index = _selfObj.initIx;
    lottery.prize = calculateAwardPositon(1); //每次点击的时候如果是后端传中奖位置，则进行赋值。总奖品为12个，位置：0-11
    //console.log(lottery.prize)
    lottery.speed = 100;
    lottery.callback = function (req) {
        $('#zhezhao').removeClass('hidden');
        $('.awarad_suc').removeClass('hidden');
        autoRun(0, 12);
    };
    roll();
    _selfObj.click = true;
}


function checkUserLogin() {

    $.post('/checkUser', function (data) {
        debugger;
        if (data.code == 0) {
            return true;
        } else {
            return false;
        }

    })

}

/**
 * ajax
 * @param data
 * @param callback
 */
function getAjax(data, callback) {
    $.ajax({
        url: '/lottery',
        type: 'post',
        data: data || {},
        success: function (req) {
            debugger;
            callback && callback(req);
        }
    })
}

/**
 * 让选择框自动移动起来
 * @param index
 * @param count
 */
function autoRun(index, count) {
    _selfObj.timerIx = setTimeout(function () {
        _selfObj.initIx = index;
        var $target = $('#lottery');
        //移除当前位置选中样式
        $target.find(".lottery-unit-" + _selfObj.initIx).removeClass("active");
        _selfObj.initIx += 1;
        //如果默认的当前位置大于总奖品数量，则默认为第一个
        if (_selfObj.initIx > count - 1) {
            _selfObj.initIx = 0;
        }
        //添加下一个位置选中样式
        $target.find(".lottery-unit-" + _selfObj.initIx).addClass("active");
        autoRun(_selfObj.initIx, count);
    }, 800)
}

/**
 * click：是否可以抽奖，反正dbouleclick
 * initIx:记录选择框自动移动到的位置
 * timeIx:记录定时器id
 * data:中奖返回结果
 * @type {{click: boolean, initIx: number, timerIx: number}}
 * @private
 */
var _selfObj = {
    click: false,
    initIx: 0,
    data: null,
    timerIx: 0
};
/**
 * 绑定点击事件
 */
$("#lottery .get_img").click(function () {
    if (_selfObj.click) {
        return false;
    } else {
        var loginFlag = false;
        debugger;
        loginFlag = checkUserLogin();
        if (loginFlag == true) {

            getAjax(null, getAwardPostion);
        } else {
           window.location.href="/login";
        }

        return false;
    }
});


autoRun(0, 12);

/**
 * 初始化插件
 */
lottery.init('lottery');



