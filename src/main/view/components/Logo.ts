/**
 *  Logo component
 */
export interface ILogoParams {
    isAnimated: boolean;
    isVisible: boolean;
}
;

export class Navigation {
    public IsAnimated: KnockoutObservable<boolean>;
    public IsVisible: KnockoutObservable<boolean>;

    constructor(params: ILogoParams) {
        this.IsAnimated = ko.observable<boolean>(params.isAnimated);
        this.IsVisible = ko.observable<boolean>(params.isVisible);

    }
}