import UIKit
import WebKit

class ViewController: UIViewController, WKScriptMessageHandler {

    var webView : WKWebView?;
    //var clickCatcher : ClickCatcher?;
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let userController:WKUserContentController = WKUserContentController();
        userController.add(self, name: "interop");
        let webViewConfig = WKWebViewConfiguration();
        webViewConfig.userContentController = userController;
        let webView = WKWebView(frame: self.view.frame, configuration: webViewConfig);
        webView.scrollView.bounces = true;
        webView.scrollView.isScrollEnabled = true;
        webView.autoresizingMask = UIViewAutoresizing.flexibleWidth.union(UIViewAutoresizing.flexibleHeight);
        let htmlPath = Bundle.main.url(forResource: "index", withExtension: "html", subdirectory: "jsres");
        let request = URLRequest(url: htmlPath!);
        webView.load(request);
        self.view.addSubview(webView);
        //let cc = ClickCatcher(frame: self.view.frame, webView: webView);
        //self.view.addSubview(cc);

        self.webView = webView;
        //self.clickCatcher = cc;
    }
    
    override func didRotate(from fromInterfaceOrientation: UIInterfaceOrientation) {
        //self.clickCatcher!.frame = self.view.frame;
    }
    
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        let s = String(describing: message.body);
        let index = s.index(s.startIndex, offsetBy: 1);
        if (s.hasPrefix("L")) {
            print("Log message: " + s.substring(from: index));
        } else if (s.hasPrefix("M")) {
            print("System message: " + s.substring(from: index));
        } else {
            print("Unrecognized message: " + s);
        }
    }
    
    /*
    class ClickCatcher : UIView {
        
        var webView : WKWebView?;
        
        init(frame: CGRect, webView : WKWebView) {
            super.init(frame: frame)
            self.webView = webView;
        }

        required init?(coder: NSCoder) {
            super.init(coder: coder);
        }
        
        override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
            invokeJsForMouseEvent(0, touches: touches);
        }
        
        override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
            invokeJsForMouseEvent(2, touches: touches);
        }
        
        override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
            invokeJsForMouseEvent(1, touches: touches);
        }
        
        func invokeJsForMouseEvent(_ type : Int, touches: Set<UITouch>) {
        	let touch = touches.first as UITouch!;
        	let currentPoint = touch?.location(in: self);
            let xratio = (currentPoint?.x)! / self.frame.width;
            let yratio = (currentPoint?.y)! / self.frame.height;
            let js = "C$ios$invokePointer(\(type), \(xratio), \(yratio));";
            webView!.evaluateJavaScript(js, completionHandler: nil);
        }
    }
    */
}
