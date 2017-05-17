/**
 *  Sidebar component
 */
export type SidebarItem = {
    displayTitle: string;
    displaySubTitle: string;
};

export interface ISidebarParams {
    title: string;
    right?: boolean;
    large?: boolean;
    items?: KnockoutObservableArray<SidebarItem>;
}
;

export class Sidebar {

    // fetch dom element here
    public sidebar = $('#sidebar');
    public icon = this.sidebar.find('sidebar-icon').toggleClass('rotate-icon');

    public items = ko.observableArray<SidebarItem>();
    public numberOfItems = ko.pureComputed(() =>{
        return this.items.Count;
    });

    public title = ko.observable<string>("");

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
        this.title(params.title);
        this.items = params.items;

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
        // TODO
    }
}