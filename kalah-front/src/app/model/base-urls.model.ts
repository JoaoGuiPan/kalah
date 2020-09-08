import { environment } from '../../environments/environment';
import { filterObjToQueryParams } from '../common/string-utils';

const { baseUrl } = environment;

export class BaseUrls {

  private endpointUrl = '';

  constructor(endpointUrl: string = '') {
    this.endpointUrl = endpointUrl;
  }

  public get environmentUrl() {
    return baseUrl;
  }

  public get root() {
    return `${baseUrl}/${this.endpointUrl}`;
  }

  public endpoint(path: string = '') {
    return this.root + path;
  }

  public byId(id: any) {
    return `${this.endpoint(`/${id}`)}`;
  }

  public filter(filter: Partial<any>) {
    return this.endpoint(`?${filterObjToQueryParams(filter)}`);
  }
}
