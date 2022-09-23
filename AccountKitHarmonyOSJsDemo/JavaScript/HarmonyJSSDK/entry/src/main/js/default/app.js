import {hmsjsb} from '@hw-hmscore/hms-js-base'
export default {
    hmsData: {
        eventCallbackMap: {}
    },
    onCreate() {
        console.info('AceApplication onCreate');
        hmsjsb.init(this.hmsData.eventCallbackMap);
    },
    onDestroy() {
        console.info('AceApplication onDestroy');
    }
};
