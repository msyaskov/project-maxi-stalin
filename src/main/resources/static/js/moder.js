const post_rows = document.getElementsByClassName("post-row");

const posts = new Map();
class Post {

    div;
    photoPosition;

    constructor(divPostRow) {
        this.div = divPostRow;
        this.photoPosition = 0;
        posts.set(this.id, this);
    }

    init() {
        this.header = firstByClass(this.div,"post-header");
        this.header.avatar = firstByClass(this.div, "post-header-avatar");
        this.header.avatar.a = firstByTag(this.header.avatar, "a");
        this.header.avatar.a.img = firstByTag(this.header.avatar.a, "img");
        this.header.info = firstByClass(this.header, "post-header-info");
        this.header.info.groupName = firstByClass(this.header, "post-header-info-group-name");
        this.header.info.publicationDate = firstByClass(this.header, "post-header-info-publish-date");
        this.header.menu = firstByClass(this.header,"post-header-menu");
        this.header.menu.button = firstByClass(this.header,"post-header-menu-button");
        this.header.menu.content = firstByClass(this.header,"post-header-menu-content");
        this.header.menu.content.publish = allByTag(this.header.menu.content, "a")[0];
        this.header.menu.content.publish.onclick = (e) => this.publish(e);
        this.header.menu.content.edit = allByTag(this.header.menu.content, "a")[1];
        this.header.menu.content.edit.onclick = (e) => this.edit(e);
        this.header.menu.content.delete = allByTag(this.header.menu.content, "a")[2];
        this.header.menu.content.delete.onclick = (e) => this.delete(e);

        this.content = firstByClass(this.div, "post-content");
        this.content.span = firstByTag(this.content, "span");
        this.content.photo = firstByClass(this.content, "post-content-photo");
        this.content.photo.ul = firstByTag(this.content.photo, "ul");
        this.content.photo.ul.li = allByTag(this.content.photo.ul, "li");
        this.content.photo.ul.img = allByTag(this.content.photo.ul, "img");
        setElementsSize(this.content.photo.ul.img, this.content.photo.clientWidth + 'px');
        setElementSize(this.content.photo.ul, this.content.photo.ul.li.length * this.content.photo.clientWidth + 'px', getImgMaxHeight(this.content.photo.ul.img) + 'px');


        this.content.photo.buttonPrevious = firstByClass(this.content.photo, "post-content-photo-prev");
        this.content.photo.buttonPrevious.post = this;
        this.content.photo.buttonPrevious.onclick = function () {
            this.post.photoPosition += this.post.content.photo.clientWidth;
            this.post.photoPosition = Math.min(this.post.photoPosition, 0);
            if (this.post.photoPosition === 0) {
                this.post.content.photo.buttonPrevious.style.display = "none";
            }
            if (this.post.photoPosition > -this.post.content.photo.clientWidth * (this.post.content.photo.ul.li.length - 1)) {
                this.post.content.photo.buttonNext.style.display = "block";
            }
            this.post.content.photo.ul.style.marginLeft = this.post.photoPosition + 'px';
        };

        this.content.photo.buttonNext = firstByClass(this.content.photo, "post-content-photo-next");
        this.content.photo.buttonNext.post = this;
        this.content.photo.buttonNext.onclick = function () {
            this.post.photoPosition -= this.post.content.photo.clientWidth;
            this.post.photoPosition = Math.max(this.post.photoPosition, -this.post.content.photo.clientWidth * (this.post.content.photo.ul.li.length - 1));
            if (this.post.photoPosition === -this.post.content.photo.clientWidth * (this.post.content.photo.ul.li.length - 1)) {
                this.post.content.photo.buttonNext.style.display = "none";
            }
            if (this.post.photoPosition < 0) {
                this.post.content.photo.buttonPrevious.style.display = "block";
            }
            this.post.content.photo.ul.style.marginLeft = this.post.photoPosition + 'px';
        };

        this.footer = firstByClass(this.div, "post-footer");
        this.footer.likes = firstByClass(this.footer, "post-footer-likes");
        this.footer.likes.span = firstByTag(this.footer.likes, "span");
        this.footer.comments = firstByClass(this.footer, "post-footer-comments");
        this.footer.comments.span = firstByTag(this.footer.comments, "span");
        this.footer.views = firstByClass(this.footer, "post-footer-views");
        this.footer.views.span = firstByTag(this.footer.views, "span");
    }

    id() {
        return attr(this.div, "post-id");
    }

    publish(e) {
        this.request("publish");
    }

    edit(e) {
        this.request("edit");
    }

    delete(e) {
        this.request("delete");
    }

    request(method) {
        $.ajax({
            type: 'POST',
            url: '/post/' + method + '-' + this.id(),
            success: function (json) {
                let data = JSON.parse(json);
                if (data.status === 200) {
                    tooltip("Выполнено");
                } else {
                    tooltip("Не выполнено");
                }
                console.log(json);
            },
            error: function (errMsg) {
                tooltip("Ошибка");
                console.log(errMsg);
            }
        });
    }

}


for (let divPostRow of post_rows) {
    let post = new Post(divPostRow); post.init();

    if (post.content.photo.ul.li.length > 0) {
        post.content.photo.style.marginTop = '10px'
        post.content.photo.style.marginBottom = '10px'

        if (post.content.photo.ul.li.length > 1) {
            post.content.photo.buttonPrevious.style.display = "none";
            post.content.photo.buttonNext.style.display = "block";
        }
    }
}

function attr(element, attribute) {
    return element.getAttribute(attribute);
}

function allByClass(root, className) {
    return root.getElementsByClassName(className);
}
function allByTag(root, tag) {
    return root.getElementsByTagName(tag);
}
function firstByClass(root, className) {
    return root.getElementsByClassName(className).item(0);
}
function firstByTag(root, tag) {
    return root.getElementsByTagName(tag).item(0);
}

function tooltip(message) {
    let tooltip = document.getElementById('tooltip');
    firstByTag(tooltip, "span").textContent = message;
    tooltip.style.display = 'block';
    setTimeout(() => {
        tooltip.style.display = 'none';
    }, 2000);
}

function getImgMaxHeight(imgTags) {
    let maxHeight = 0;
    for (let img of imgTags) {
        maxHeight = Math.max(maxHeight, img.height);
    }
    return maxHeight;
}

function setElementSize(element, width, height) {
    if (width !== undefined) {
        element.style.width = width;
    }
    if (height !== undefined) {
        element.style.height = height;
    }
}

function setElementsSize(elements, width, height) {
    for (let element of elements) {
        setElementSize(element, width, height);
    }
}


