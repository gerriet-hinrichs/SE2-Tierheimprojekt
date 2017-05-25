/**
 *  Navigation component
 */
export type NavigationItem = {
  Name: string;
  Title: string;
  IsSelected: KnockoutObservable<boolean>;
};

export interface INavigationParams {
    items: NavigationItem[];
}
;

export class Navigation {
    public itemList = ko.observableArray<NavigationItem>([]);

    constructor(params: INavigationParams) {
        this.itemList(params.items);
    }

    public navigate(item: NavigationItem) {
        console.log(item.Name + " clicked.");
    }
}