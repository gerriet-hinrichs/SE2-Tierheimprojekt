/**
 * Created by adria on 17.05.2017.
 */
/**
 *  Radiobutton component
 */
import "jquery";

interface IRadioButtonParams {

}
;

export class radiobutton {

    // fetch dom element here
    //public radiobutton = $('#radiobutton');

    private _isChecked = ko.observable<boolean>(true);
    public isChecked = ko.pureComputed({
        read: () => {
            return this._isChecked();
        },
        write: c => {
            this._isChecked(c);
        }
    }).extend({deffered: true, notify: 'always'});

    constructor(params: IRadioButtonParams) {

        this.isChecked.subscribe(c => {
            // TODO
        });

        this._isChecked.subscribe(c => {
            // TODO
        });
    }
}