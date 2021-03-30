#include "pch.h"
#include "CircleView.h"
#include "CircleView.g.cpp"

#include "JSValueXaml.h"
#include "Utils.h"

using namespace winrt;
using namespace Microsoft::Graphics::Canvas;
using namespace Microsoft::ReactNative;

namespace winrt::RNSVG::implementation {
void CircleView::UpdateProperties(IJSValueReader const &reader, bool forceUpdate, bool invalidate) {
  const JSValueObject &propertyMap = JSValue::ReadObjectFrom(reader);

  for (auto const &pair : propertyMap) {
    auto const &propertyName = pair.first;
    auto const &propertyValue = pair.second;

    if (propertyName == "r") {
      m_r = SVGLength::From(propertyValue);
    } else if (propertyName == "cx") {
      m_cx = SVGLength::From(propertyValue);
    } else if (propertyName == "cy") {
      m_cy = SVGLength::From(propertyValue);
    }
  }

  __super::UpdateProperties(reader, forceUpdate, invalidate);
}

void CircleView::CreateGeometry(UI::Xaml::CanvasControl const &canvas) {
  auto resourceCreator{canvas.try_as<ICanvasResourceCreator>()};

  auto cx{Utils::GetSvgLengthValue(m_cx, canvas.Size().Width)};
  auto cy{Utils::GetSvgLengthValue(m_cy, canvas.Size().Height)};
  auto r{Utils::GetSvgLengthValue(m_r, canvas.Size().Height)};

  Geometry(Geometry::CanvasGeometry::CreateCircle(resourceCreator, cx, cy, r));
}
} // namespace winrt::RNSVG::implementation
