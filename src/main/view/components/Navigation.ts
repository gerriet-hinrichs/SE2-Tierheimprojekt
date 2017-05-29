/**
 *  Navigation component
 */
export type NavigationItem = {
    Name: string;
    Title: string;
    IsSelected: KnockoutObservable<boolean>;
};

export interface INavigationParams {
    items: KnockoutObservableArray<NavigationItem>;
    currentComponent: KnockoutObservable<string>;
}
;

export class Navigation {
    public itemList: KnockoutObservableArray<NavigationItem>;
    public currentComponent: KnockoutObservable<string>;

    constructor(params: INavigationParams) {
        this.currentComponent = params.currentComponent;
        this.itemList = params.items;
    }

    public navigate(item: NavigationItem) {
        for (let item of this.itemList()) {
            item.IsSelected(false);// = ko.observable<boolean>(false);
        }

        item.IsSelected(true);
        this.currentComponent(item.Name);
    }
}