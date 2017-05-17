/**
 * Created by adria on 17.05.2017.
 */
/**
 *  Checkbox component
 */
import "jquery";

interface ICheckBoxParams {

}
;

export class checkbox {

    // fetch dom element here
    //public checkbox = $('#checkbox');

    private _isChecked = ko.observable<boolean>(true);
    public isChecked = ko.pureComputed({
        read: () => {
            return this._isChecked();
        },
        write: c => {
            this._isChecked(c);
        }
    }).extend({deffered: true, notify: 'always'});

    constructor(params: ICheckBoxParams) {

        this.isChecked.subscribe(c => {
            // TODO
        });

        this._isChecked.subscribe(c => {
            // TODO
        });
    }
}