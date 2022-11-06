export interface Cookie {
    name: string;
    value: string;
    path?: string;
    domain?: string;
    expires?: string;
    rawExpires?: string;
    maxAge?: number;
    secure?: boolean;
    httpOnly?: boolean;
    sameSite?: string;
    unparsed?: string;
}
export interface CycleTLSRequestOptions {
    headers?: {
        [key: string]: any;
    };
    cookies?: Array<object> | {
        [key: string]: string;
    };
    body?: string;
    ja3?: string;
    userAgent?: string;
    proxy?: string;
    timeout?: number;
    disableRedirect?: boolean;
    headerOrder?: string[];
}
export interface CycleTLSResponse {
    status: number;
    body: string | {
        [key: string]: any;
    };
    headers: {
        [key: string]: any;
    };
}
export interface CycleTLSClient {
    (url: string, options: CycleTLSRequestOptions, method?: "head" | "get" | "post" | "put" | "delete" | "trace" | "options" | "connect" | "patch"): Promise<CycleTLSResponse>;
    head(url: string, options: CycleTLSRequestOptions): Promise<CycleTLSResponse>;
    get(url: string, options: CycleTLSRequestOptions): Promise<CycleTLSResponse>;
    post(url: string, options: CycleTLSRequestOptions): Promise<CycleTLSResponse>;
    put(url: string, options: CycleTLSRequestOptions): Promise<CycleTLSResponse>;
    delete(url: string, options: CycleTLSRequestOptions): Promise<CycleTLSResponse>;
    trace(url: string, options: CycleTLSRequestOptions): Promise<CycleTLSResponse>;
    options(url: string, options: CycleTLSRequestOptions): Promise<CycleTLSResponse>;
    connect(url: string, options: CycleTLSRequestOptions): Promise<CycleTLSResponse>;
    patch(url: string, options: CycleTLSRequestOptions): Promise<CycleTLSResponse>;
    exit(): Promise<undefined>;
}
declare const initCycleTLS: (initOptions?: {
    port?: number;
    debug?: boolean;
}) => Promise<CycleTLSClient>;
export default initCycleTLS;
