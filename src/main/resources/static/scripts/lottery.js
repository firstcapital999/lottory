/**
 * Created by 黄旭晖 on 2017/10/24.
 */

var lottery={
    index:-1,	//当前转动到哪个位置，起点位置
    count:0,	//总共有多少个位置
    timer:0,	//setTimeout的ID，用clearTimeout清除
    speed:20,	//初始转动速度
    times:0,	//转动次数
    cycle:50,	//转动基本次数：即至少需要转动多少次再进入抽奖环节 ，顺时针旋转
    prize:-1,	//中奖位置
    init:function(id){
        /**
         * 获取奖品数量
         */
        if ($("#"+id).find(".lottery-unit").length>0) {
            var $lottery = $("#"+id);
            var $units = $lottery.find(".lottery-unit");
            this.obj = $lottery;
            this.count = $units.length;
            $lottery.find(".lottery-unit-"+this.index).addClass("active");
        }
    },
    roll:function(){
        /**
         * 每次旋转定位选中位置
         */
        var index = this.index;
        var count = this.count;
        var lottery = this.obj;
        //移除当前位置选中样式
        $(lottery).find(".lottery-unit-"+index).removeClass("active");
        index += 1;
        //如果默认的当前位置大于总奖品数量，则默认为第一个
        if (index>count-1) {
            index = 0;
        }
        //添加下一个位置选中样式
        $(lottery).find(".lottery-unit-"+index).addClass("active");
        this.index=index;
        return false;
    },
    stop:function(index){
        this.prize=index;
        return false;
    }
};
/**
 * 根据已转动的次数判断当前的速度和最终停止的位置
 * @returns {boolean}
 */
function roll(){
    lottery.times += 1;
    lottery.roll();
    if (lottery.times > lottery.cycle +10 && lottery.prize==lottery.index) {
        clearTimeout(lottery.timer);
        lottery.prize=-1;
        lottery.times=0;
        click=false;
    }else{

        if (lottery.times<lottery.cycle) {
            lottery.speed -= 10;
        }
        else if(lottery.times==lottery.cycle) {
            //这个给随机中奖位置，如果是后端传值，则不用。
//                        var index = Math.random()*(lottery.count)|0;
//                        lottery.prize = index;
            //兼容后端传中奖位置，如果是随机，则不用
            (this.count-1<this.prize) && (this.prize = 0);// 如果中奖位置大于总奖品数，则默认为第一个中奖
        }
        else{
            if (lottery.times > lottery.cycle+10 && ((lottery.prize==0 && lottery.index==7) || lottery.prize==lottery.index+1)) {
                lottery.speed += 110;
            }else{
                lottery.speed += 20;
            }
        }
        if (lottery.speed<40) {
            lottery.speed=40;
        };
        console.log(lottery.times+'^^^^^^'+lottery.speed+'^^^^^^^'+lottery.prize);
        lottery.timer = setTimeout(roll,lottery.speed);
    }
    return false;
}
var click=false;
window.onload=function(){
    /**
     * 初始化插件
     */
    lottery.init('lottery');
    $("#lottery .get_img").click(function(){
        if (click) {
            return false;
        }else{
            lottery.prize = 2; //每次点击的时候如果是后端传中奖位置，则进行赋值。总奖品为12个，位置：0-11
            lottery.speed=100;
            roll();
            click=true;
            return false;
        }
    });
};