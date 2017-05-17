/**
 *  Sidebar component
 */

interface ISidebarParams {
    right?: boolean;
    large?: boolean;
}
;

class sidebar {

    public sidebar = $('#sidebar');
    public icon = this.sidebar.find('sidebar-icon').toggleClass('rotate-icon');

    private _isOpen = ko.observable<boolean>(true);
    public isOpen = ko.pureComputed({
        read: () => {
            return this._isOpen();
        },
        write: s => {
            this._isOpen(s);
        }
    }).extend({deffered: true, notify: 'always'});

    constructor(params: ISidebarParams) {
        console.log("sidebar with: ", params);

        this.isOpen.subscribe(s => {
            this.toggleVisibility(s);
        });

        this._isOpen.subscribe(s => {
            this.rotateIcon();
        });
    }

    private toggleVisibility(visible: boolean) {
        if (visible && !this.sidebar.hasClass('visible')) {
            this.sidebar.addClass('visible');
        } else {
            this.sidebar.removeClass('visible');
        }
    }

    private rotateIcon() {

    }
}

export = sidebar;